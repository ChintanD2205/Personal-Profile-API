CREATE PROCEDURE sp_get_user_roles
    @UserId INT
AS
BEGIN
    SET NOCOUNT ON;

    SELECT r.RoleName
    FROM ROLES r
    JOIN USER_ROLE_ASSIGNMENTS ur ON r.RoleID = ur.RoleID
    WHERE ur.EmployeeID = @UserId;

    IF @@ROWCOUNT = 0
    BEGIN
        SELECT 'CLIENT' AS roleName;
    END
END
