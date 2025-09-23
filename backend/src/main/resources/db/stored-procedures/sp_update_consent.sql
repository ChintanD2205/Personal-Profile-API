CREATE OR ALTER PROCEDURE sp_update_consent
    @UserID         INT,
    @Purpose        VARCHAR(250),
    @Granted        BIT,
    @ConsentScope   VARCHAR(MAX)
AS
BEGIN
    SET NOCOUNT ON;

    DECLARE @ExistingConsentID INT;
    DECLARE @CurrentGranted BIT;
    DECLARE @Action VARCHAR(100);
    DECLARE @Details VARCHAR(MAX);


    SELECT 
        @ExistingConsentID = ConsentID,
        @CurrentGranted = Granted
    FROM dbo.CONSENT_RECORDS
    WHERE UserID = @UserID AND Purpose = @Purpose;

    IF @ExistingConsentID IS NULL

        BEGIN

            INSERT INTO dbo.CONSENT_RECORDS (UserID, Purpose, Granted, ConsentScope, ConsentTimestamp, WithdrawnAt)
            VALUES (@UserID, @Purpose, @Granted, @ConsentScope, GETDATE(), NULL);

            SET @ExistingConsentID = SCOPE_IDENTITY();
            SET @Action = 'Inserted Consent';
        END
    ELSE

    BEGIN
        IF (@CurrentGranted = 1 AND @Granted = 0)

            BEGIN
                UPDATE dbo.CONSENT_RECORDS
                SET Granted = 0,
                    ConsentScope = NULL,
                    WithdrawnAt = GETDATE(),
                    ConsentTimestamp = GETDATE()
                WHERE ConsentID = @ExistingConsentID;

                SET @Action = 'Withdrawn Consent';
            END

        ELSE IF (@CurrentGranted = 0 AND @Granted = 1)

            BEGIN
                UPDATE dbo.CONSENT_RECORDS
                SET Granted = 1,
                    ConsentScope = @ConsentScope,
                    WithdrawnAt = NULL,
                    ConsentTimestamp = GETDATE()
                WHERE ConsentID = @ExistingConsentID;

                SET @Action = 'Re-granted Consent';
            END
        ELSE

            BEGIN
                UPDATE dbo.CONSENT_RECORDS
                SET Granted = @Granted,
                    ConsentScope = @ConsentScope,
                    ConsentTimestamp = GETDATE()
                WHERE ConsentID = @ExistingConsentID;

                SET @Action = 'Updated Consent';
            END
    END


    SET @Details = 'Purpose: ' + @Purpose + ', Granted: ' + CAST(@Granted AS VARCHAR(5));
    EXEC sp_update_audit 
        @CustomerUserID = @UserID, 
        @Action = @Action, 
        @Details = @Details;


    SELECT @ExistingConsentID AS NewConsentId;
END;
GO



SELECT * FROM CONSENT_RECORDS;