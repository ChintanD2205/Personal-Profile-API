CREATE OR ALTER PROCEDURE sp_view_data_access
	@UserID			INT,
	@InternalUID	INT=NULL
AS
BEGIN
	SET NOCOUNT ON
	
	IF @UserID IS NULL
	BEGIN
		SELECT * FROM AUDIT_LOG;

		DECLARE @CUID INT;
		DECLARE user_cursor CURSOR FOR
			SELECT DISTINCT CustomerUserID
			FROM AUDIT_LOG;

		OPEN user_cursor;
		FETCH NEXT FROM user_cursor INTO @CUID;

		WHILE @@FETCH_STATUS = 0
		BEGIN
			EXEC sp_update_audit
				@CustomerUserID = @CUID, 
				@Action = 'VIEW_DATA_USAGE', 
				@Details = 'Client Data Usage Viewed',
				@InternalUserID = @InternalUID;

			FETCH NEXT FROM user_cursor INTO @CUID;
		END

		CLOSE user_cursor;
		DEALLOCATE user_cursor;
	END
	ELSE
	BEGIN
		SELECT * FROM AUDIT_LOG WHERE CustomerUserID = @UserID;

		EXEC sp_update_audit 
			@CustomerUserID = @UserID, 
			@Action = 'VIEW_DATA_USAGE', 
			@Details = 'Client Data Usage Viewed',
			@InternalUserID = @InternalUID;
	END
END;
GO