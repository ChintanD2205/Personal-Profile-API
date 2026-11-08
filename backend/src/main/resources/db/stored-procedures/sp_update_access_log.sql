CREATE PROCEDURE sp_update_access_log
    @UserID INT = NULL,
    @Endpoint VARCHAR(255),
    @HttpMethod VARCHAR(10),
    @RequestTime DATETIME,
    @IpAddress VARCHAR(50),
    @StatusCode INT
AS
BEGIN
    INSERT INTO ACCESS_LOGS (UserID, Endpoint, HttpMethod, RequestTime, IpAddress, StatusCode)
    VALUES (@UserID, @Endpoint, @HttpMethod, @RequestTime, @IpAddress, @StatusCode);
END;
