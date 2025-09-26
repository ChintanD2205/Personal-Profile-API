CREATE OR ALTER PROCEDURE sp_restore_soft_deleted_profile
	@UserID		INT,
	@InternalUID INT
AS
BEGIN
	
	IF EXISTS(SELECT 1 FROM CLIENTS WHERE UserID=@UserID)
	BEGIN
		UPDATE CLIENTS 
		SET Status=1, UpdatedAt=GETDATE()
		WHERE UserID=@UserID;

		UPDATE PRIVACY_REQUESTS
		SET Status='REJECTED', CompletedAt=GETDATE(), Notes='Client Account Deletion Request Rejected'
		WHERE UserID=@UserID;

		EXEC sp_update_audit 
			@CustomerUserID = @UserID, 
			@Action = 'Restore', 
			@Details = 'Restored Soft Deleted Client',
			@InternalUserID = @InternalUID;
	END
	SELECT * FROM CLIENTS WHERE UserID=@UserID;
END;
GO