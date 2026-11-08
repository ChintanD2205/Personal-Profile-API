CREATE OR ALTER PROCEDURE sp_internal_user_create

	@Username	VARCHAR(100),
	@Password	VARCHAR(250),
	@Email		VARCHAR(250),
	@FirstName	VARCHAR(250),
	@LastName	VARCHAR(250),
	@Department	VARCHAR(250),
	@LastLogin	DATETIME=NULL,
	@Status		BIT	
AS
BEGIN
	SET NOCOUNT ON;
	IF EXISTS (SELECT 1 FROM INTERNAL_USERS WHERE Email = @Email)
	BEGIN
        PRINT 'A user with this email already exists.';
        RETURN;
    END

	INSERT INTO INTERNAL_USERS (Username, Password, Email, FirstName, LastName, Department, CreatedAt, LastLogin, Status)
    VALUES (@Username, @Password, @Email, @FirstName, @LastName, @Department, GETDATE(), @LastLogin, @Status);

    PRINT 'User inserted successfully.';

    SELECT SCOPE_IDENTITY() AS NewUserId;

END;
GO

--EXEC sp_internal_user_create
--    @Username = 'harshit.vara',
--    @Password = 'hpv@123',
--    @Email = 'harshit.vara@google.com',
--    @FirstName = 'Harshit',
--    @LastName = 'Vara',
--	@Department='IT',
--    @Status = 1;

SELECT * FROM INTERNAL_USERS;