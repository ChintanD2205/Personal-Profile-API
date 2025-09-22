CREATE OR ALTER PROCEDURE sp_update_audit
    @CustomerUserID     INT,
    @Action             VARCHAR(250),
    @Details            VARCHAR(MAX) = NULL,
    @InternalUserID     INT = NULL
AS
BEGIN
    SET NOCOUNT ON;

    INSERT INTO dbo.AUDIT_LOG (CustomerUserID, Action, Details, InternalUserID)
    VALUES (@CustomerUserID, @Action, @Details, @InternalUserID);
END;
GO


EXEC sp_update_audit
    @CustomerUserID = 1,
	@Action = 'PROFILE_CREATED',
	@Details='User self-registration';


SELECT * FROM AUDIT_LOG;