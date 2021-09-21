const urlBlockedUsers = "http://localhost:8080/blocked"

showBlockedUsers()

function showBlockedUsers() {
    document.getElementById("blockedUsers")
    {
        fetchTable(urlBlockedUsers, options, "#blockedUsersTable");
    }
}