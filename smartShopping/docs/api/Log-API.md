# Log API Documentation (Admin)

Base URL: `/api/admin/logs`

## Table of Contents
1. [Get All Logs](#1-get-all-logs)

---

## 1. Get All Logs

Lấy tất cả system logs (ADMIN only).

**Endpoint:** `GET /api/admin/logs`

**Headers:**
```
Authorization: Bearer {accessToken}
```

**Response Success (200):**
```json
{
  "success": true,
  "code": 200,
  "message": "Logs retrieved successfully",
  "data": {
    "logs": [
      {
        "id": 1,
        "level": "INFO",
        "message": "User logged in successfully",
        "userId": 1,
        "username": "john@example.com",
        "action": "LOGIN",
        "ipAddress": "192.168.1.1",
        "userAgent": "Mozilla/5.0...",
        "endpoint": "/api/user/login",
        "method": "POST",
        "statusCode": 200,
        "duration": 145,
        "timestamp": "2026-01-02T10:00:00"
      },
      {
        "id": 2,
        "level": "ERROR",
        "message": "Failed login attempt - Invalid password",
        "userId": null,
        "username": "test@example.com",
        "action": "LOGIN_FAILED",
        "ipAddress": "192.168.1.2",
        "userAgent": "PostmanRuntime/7.32.0",
        "endpoint": "/api/user/login",
        "method": "POST",
        "statusCode": 401,
        "duration": 89,
        "timestamp": "2026-01-02T10:05:00"
      },
      {
        "id": 3,
        "level": "WARN",
        "message": "Multiple failed login attempts detected",
        "userId": null,
        "username": "test@example.com",
        "action": "SECURITY_ALERT",
        "ipAddress": "192.168.1.2",
        "userAgent": "PostmanRuntime/7.32.0",
        "endpoint": "/api/user/login",
        "method": "POST",
        "statusCode": 429,
        "duration": 12,
        "timestamp": "2026-01-02T10:10:00"
      },
      {
        "id": 4,
        "level": "INFO",
        "message": "Food created successfully",
        "userId": 1,
        "username": "john@example.com",
        "action": "CREATE_FOOD",
        "ipAddress": "192.168.1.1",
        "userAgent": "SmartShopping-Mobile/1.0",
        "endpoint": "/api/food",
        "method": "POST",
        "statusCode": 200,
        "duration": 234,
        "timestamp": "2026-01-02T10:15:00"
      },
      {
        "id": 5,
        "level": "INFO",
        "message": "Shopping list completed",
        "userId": 1,
        "username": "john@example.com",
        "action": "COMPLETE_SHOPPING_LIST",
        "ipAddress": "192.168.1.1",
        "userAgent": "SmartShopping-Mobile/1.0",
        "endpoint": "/api/shopping-lists/1/complete",
        "method": "POST",
        "statusCode": 200,
        "duration": 178,
        "timestamp": "2026-01-02T10:20:00"
      }
    ],
    "totalLogs": 5,
    "summary": {
      "infoCount": 3,
      "warnCount": 1,
      "errorCount": 1,
      "totalRequests": 5,
      "averageDuration": 131.6
    }
  }
}
```

**Error Response (401) - Unauthorized:**
```json
{
  "success": false,
  "code": 401,
  "message": "Unauthorized - Admin access required"
}
```

**Error Response (403) - Forbidden:**
```json
{
  "success": false,
  "code": 403,
  "message": "Forbidden - Insufficient permissions"
}
```

**Error Response (500):**
```json
{
  "success": false,
  "code": 500,
  "message": "Failed to retrieve logs: error details"
}
```

---

## Error Codes Summary

| Code | Description |
|------|-------------|
| 200 | Logs retrieved successfully |
| 401 | Unauthorized - Invalid or missing token |
| 403 | Forbidden - Not an admin user |
| 500 | Internal server error |

---

## Log Levels

| Level | Description | Usage |
|-------|-------------|-------|
| INFO | Informational messages | Successful operations, normal events |
| WARN | Warning messages | Potential issues, suspicious activities |
| ERROR | Error messages | Failed operations, exceptions |
| DEBUG | Debug messages | Detailed debugging information (dev only) |

---

## Common Actions Logged

| Action | Description |
|--------|-------------|
| LOGIN | User login |
| LOGOUT | User logout |
| LOGIN_FAILED | Failed login attempt |
| REGISTER | User registration |
| PASSWORD_CHANGE | Password changed |
| PASSWORD_RESET | Password reset |
| EMAIL_VERIFY | Email verification |
| CREATE_FOOD | Food item created |
| UPDATE_FOOD | Food item updated |
| DELETE_FOOD | Food item deleted |
| CREATE_SHOPPING_LIST | Shopping list created |
| COMPLETE_SHOPPING_LIST | Shopping list completed |
| CREATE_MEAL | Meal plan created |
| CREATE_RECIPE | Recipe created |
| SECURITY_ALERT | Security-related alerts |

---

## Notes

1. **Authentication**: Endpoint yêu cầu `Authorization: Bearer {accessToken}` với quyền ADMIN
2. **Access Control**: Chỉ ADMIN mới có quyền xem logs
3. **Log Information**:
   - User activity (userId, username)
   - Request details (endpoint, method, statusCode)
   - Performance metrics (duration in milliseconds)
   - Client info (ipAddress, userAgent)
4. **Privacy**: Sensitive data (passwords, tokens) không được log
5. **Retention**: Logs có thể được archive hoặc delete sau một khoảng thời gian
6. **Performance**: Duration tính bằng milliseconds (ms)
7. **Filtering**: Có thể thêm query params để filter logs:
   - `?level=ERROR` - Chỉ lấy error logs
   - `?userId=1` - Logs của user cụ thể
   - `?action=LOGIN` - Logs của action cụ thể
   - `?startDate=2026-01-01&endDate=2026-01-31` - Logs trong khoảng thời gian
8. **Pagination**: Có thể thêm pagination cho large datasets:
   - `?page=1&size=50`
9. **Export**: Admin có thể export logs ra CSV hoặc JSON file
10. **Monitoring**: Logs được dùng cho:
    - Security monitoring
    - Performance analysis
    - Debugging issues
    - Audit trail
    - User behavior analysis

---

## Examples

### Using cURL

```bash
curl -X GET http://localhost:8080/api/admin/logs \
  -H "Authorization: Bearer ADMIN_ACCESS_TOKEN"
```

### Using JavaScript (Fetch API)

```javascript
fetch('http://localhost:8080/api/admin/logs', {
  method: 'GET',
  headers: {
    'Authorization': `Bearer ${adminToken}`
  }
})
.then(response => response.json())
.then(data => {
  console.log('Total logs:', data.data.totalLogs);
  console.log('Summary:', data.data.summary);
  data.data.logs.forEach(log => {
    console.log(`[${log.level}] ${log.timestamp}: ${log.message}`);
  });
});
```

### Using Postman

1. Method: GET
2. URL: `http://localhost:8080/api/admin/logs`
3. Headers: `Authorization: Bearer {adminToken}`
4. Send request

### Filtering Examples (Future Enhancement)

```
GET /api/admin/logs?level=ERROR
GET /api/admin/logs?userId=1
GET /api/admin/logs?action=LOGIN_FAILED
GET /api/admin/logs?startDate=2026-01-01&endDate=2026-01-31
GET /api/admin/logs?page=1&size=50
```
