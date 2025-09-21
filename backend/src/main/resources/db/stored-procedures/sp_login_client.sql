CREATE OR ALTER PROCEDURE sp_login_client

	@Email		VARCHAR(100),
	@Password	VARCHAR(250)
AS
BEGIN
	SELECT UserID, FirstName, LastName, Username from CLIENTS WHERE Email=@Email AND Password=@Password;
END;
GO
