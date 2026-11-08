CREATE OR ALTER PROCEDURE sp_get_all_marketing_users
	@InternalUID	INT
AS
BEGIN
	SET NOCOUNT ON
	SELECT c.UserID, c.Email 
	FROM CLIENTS c
	INNER JOIN CONSENT_RECORDS cr
	ON c.UserID=cr.UserID
	WHERE cr.Purpose='MARKETING_EMAILS' AND cr.Granted=1;

	DECLARE @CUID INT;
	DECLARE user_cursor CURSOR FOR
		SELECT DISTINCT c.UserID
		FROM CLIENTS c
		INNER JOIN CONSENT_RECORDS cr ON c.UserID=cr.UserID
		WHERE cr.Purpose='MARKETING_EMAILS' AND cr.Granted=1;

	OPEN user_cursor;
	FETCH NEXT FROM user_cursor INTO @CUID;

	WHILE @@FETCH_STATUS=0
	BEGIN
		EXEC sp_update_audit 
			@CustomerUserID = @CUID, 
			@Action = 'VIEW_MARKETING_PREFERENCES', 
			@Details = 'Marketing Preferences Viewed (Bulk Access)',
			@InternalUserID = @InternalUID;

		FETCH NEXT FROM user_cursor INTO @CUID;
	END

	CLOSE user_cursor;
	DEALLOCATE user_cursor;
END;
GO