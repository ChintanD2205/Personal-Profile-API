CREATE OR ALTER PROCEDURE sp_get_analytics
	@InternalUID	INT
AS
BEGIN
	SET NOCOUNT ON;
	
	SELECT 
		(SELECT COUNT(*) 
		FROM view_analytics_consented_users 
		WHERE Status = 1) 
		AS totalActiveUsers,

		(SELECT COUNT(*) 
		FROM view_analytics_consented_users 
		WHERE Status = 0) 
		AS erasureRequested,
		
		(SELECT COUNT(*) 
		 FROM view_analytics_consented_users c 
		 INNER JOIN CONSENT_RECORDS cr 
		 ON c.UserID = cr.UserID 
		 WHERE cr.Purpose = 'MARKETING_EMAILS' AND cr.Granted = 1) 
		 AS marketingOptIn,

		(SELECT COUNT(*) 
		 FROM view_analytics_consented_users c 
		 INNER JOIN CONSENT_RECORDS cr 
		 ON c.UserID = cr.UserID 
		 WHERE cr.Purpose = 'MARKETING_EMAILS' AND cr.Granted = 0) 
		 AS marketingOptOut,
		
		(SELECT COUNT(*) FROM ACCESS_LOGS) AS totalApiCalls,
		(SELECT TOP 1 Endpoint 
		FROM ACCESS_LOGS 
		GROUP BY Endpoint 
		ORDER BY COUNT(*) DESC) 
		AS mostAccessedEndpoint,
		
		(SELECT COUNT(*) FROM AUDIT_LOG al 
		 WHERE al.Details LIKE '%hard deleted%'
		   AND al.CustomerUserID IN 
		   (SELECT UserID FROM view_analytics_consented_users))
		   AS erasureCompleted;
	
	EXEC sp_update_audit 
		@CustomerUserID = NULL, 
		@Action = 'VIEW_ANALYTICS', 
		@Details = 'Privacy-compliant analytics viewed',
		@InternalUserID = @InternalUID;
END;
GO

EXEC sp_get_analytics @InternalUID=999;