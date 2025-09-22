CREATE OR ALTER PROCEDURE sp_get_client
	@UserID			INT,
	@InternalUID	INT=NULL
AS
BEGIN
	SET NOCOUNT ON
	SELECT * FROM CLIENTS WHERE UserID=@UserID;

	EXEC sp_update_audit 
        @CustomerUserID = @UserID, 
        @Action = 'VIEW_CLIENT', 
        @Details = 'Client Profile Viewed',
		@InternalUserID = @InternalUID;
END;
GO