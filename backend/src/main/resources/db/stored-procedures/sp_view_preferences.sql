CREATE OR ALTER PROCEDURE sp_view_preferences
	@UserID			INT,
	@InternalUID	INT = NULL
AS
BEGIN
	SET NOCOUNT ON
	SELECT * FROM CONSENT_RECORDS WHERE UserID=@UserID;

	EXEC sp_update_audit 
        @CustomerUserID = @UserID, 
        @Action = 'VIEW_PREFERENCES', 
        @Details = 'Client Preferences Viewed',
		@InternalUserID = @InternalUID;
END;
GO