CREATE OR ALTER PROCEDURE profile_create_client

	@Username	VARCHAR(100),
	@Password	VARCHAR(250),
	@Email		VARCHAR(250),
	@FirstName	VARCHAR(250),
	@LastName	VARCHAR(250),
	@Status		BIT=1
AS
BEGIN
	SET NOCOUNT ON;
	IF EXISTS (SELECT 1 FROM CLIENTS WHERE Email = @Email)
	BEGIN
        PRINT 'A client with this email already exists.';
        RETURN;
    END

	INSERT INTO CLIENTS (Username, Password, Email, FirstName, LastName, CreatedAt, UpdatedAt, Status)
    VALUES (@Username, @Password, @Email, @FirstName, @LastName, GETDATE(), GETDATE(), @Status);

    PRINT 'Client inserted successfully.';

	EXEC sp_update_audit @CustomerUserID = CAST(SCOPE_IDENTITY() AS INT), @Action = 'PROFILE_CREATED', @Details='User self-registration';

    SELECT CAST(SCOPE_IDENTITY() AS INT) AS NewUserId;

END;
GO

--EXEC profile_create_client
--    @Username = 'chintan_d',
--    @Password = 'securePass123',
--    @Email = 'chintan222005@gmail.com',
--    @FirstName = 'Chintan',
--    @LastName = 'Dodia',
--    @Status = 1;
SELECT * FROM CLIENTS;