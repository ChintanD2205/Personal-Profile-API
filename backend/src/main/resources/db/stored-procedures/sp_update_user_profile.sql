CREATE OR ALTER PROCEDURE sp_update_user_profile
	@Username	VARCHAR(100),
	@FirstName	VARCHAR(250),
	@LastName	VARCHAR(250),
	@UserID		INT,
	@InternalUID INT
AS
BEGIN
	
	UPDATE CLIENTS 
	SET Username=@Username, FirstName=@FirstName, LastName=@LastName, UpdatedAt=GETDATE()
	WHERE UserID=@UserID AND Status=1;

	SELECT * FROM CLIENTS WHERE UserID=@UserID;

	EXEC sp_update_audit 
		@CustomerUserID = @UserID, 
		@Action = 'Updated Client Data', 
		@Details = 'Client Data updated as requested',
		@InternalUserID = @InternalUID;


END;
GO