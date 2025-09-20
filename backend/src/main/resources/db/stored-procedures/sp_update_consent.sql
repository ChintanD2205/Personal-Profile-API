CREATE OR ALTER PROCEDURE sp_update_consent

	@UserID			INT,
	@Purpose		VARCHAR(250),
	@Granted		BIT,
	@ConsentScope	VARCHAR(MAX)
AS
BEGIN
	SET NOCOUNT ON;

	INSERT INTO dbo.CONSENT_RECORDS (UserID, Purpose, Granted, ConsentScope)
    VALUES (@UserID, @Purpose, @Granted, @ConsentScope);

    PRINT 'Consent Updated';

    SELECT SCOPE_IDENTITY() AS NewConsentId;

END;
GO

EXEC sp_update_consent
    @UserID = 1,
	@Purpose = 'MARKETING_EMAILS',
	@Granted = 1,
	@ConsentScope = 'email,firstName,lastName';


SELECT * FROM CONSENT_RECORDS;