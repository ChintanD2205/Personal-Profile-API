CREATE OR ALTER PROCEDURE sp_get_marketing_users_by_id
	@UserID			INT,
	@InternalUID	INT
AS
BEGIN
	SET NOCOUNT ON

	SELECT c.UserID, c.Email 
	FROM CLIENTS c
	INNER JOIN CONSENT_RECORDS cr
	ON c.UserID=cr.UserID
	WHERE cr.Purpose='MARKETING_EMAILS' AND cr.Granted=1 AND c.UserID=@UserID;

	EXEC sp_update_audit 
        @CustomerUserID = @UserID, 
        @Action = 'VIEW_MARKETING_PREFERENCES', 
        @Details = 'Marketing Preferences Viewed',
		@InternalUserID = @InternalUID;
END;
GO