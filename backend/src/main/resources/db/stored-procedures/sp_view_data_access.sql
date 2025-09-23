CREATE OR ALTER PROCEDURE sp_view_data_access
	@UserID			INT,
	@InternalUID	INT=NULL
AS
BEGIN
	SET NOCOUNT ON
	SELECT * FROM AUDIT_LOG WHERE CustomerUserID=@UserID;

	EXEC sp_update_audit 
        @CustomerUserID = @UserID, 
        @Action = 'VIEW_DATA_USAGE', 
        @Details = 'Client Data Usage Viewed',
		@InternalUserID = @InternalUID;
END;
GO