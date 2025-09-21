CREATE OR ALTER PROCEDURE sp_get_user_roles
    @UserId INT
AS
BEGIN
    SET NOCOUNT ON;
    
    IF EXISTS (SELECT 1 FROM USER_ROLE_ASSIGNMENTS WHERE EmployeeID = @UserId)
    BEGIN
        SELECT r.RoleName
        FROM ROLES r
        JOIN USER_ROLE_ASSIGNMENTS ur ON r.RoleID = ur.RoleID
        WHERE ur.EmployeeID = @UserId;
    END
    ELSE
    BEGIN
        SELECT 'CLIENT' AS RoleName;
    END
END

EXEC sp_get_user_roles @UserId=1;