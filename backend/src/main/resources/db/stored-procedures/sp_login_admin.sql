CREATE OR ALTER PROCEDURE sp_login_admin

	@Email		VARCHAR(100),
	@Password	VARCHAR(250)
AS
BEGIN
	SELECT * from INTERNAL_USERS WHERE Email=@Email AND Password=@Password;
END;
GO