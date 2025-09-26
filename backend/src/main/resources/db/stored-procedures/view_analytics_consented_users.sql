CREATE OR ALTER VIEW view_analytics_consented_users 

AS

SELECT c.*
FROM CLIENTS c
INNER JOIN CONSENT_RECORDS cr 
ON c.UserID = cr.UserID
WHERE cr.Purpose = 'ANALYTICS_TRACKING' AND cr.Granted = 1;

GO