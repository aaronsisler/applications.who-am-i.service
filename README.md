# applications.who-am-i.service

## How to use

1. Open the newly copied shell in VS Code
2. Think about what you might need i.e. integration tests, contracts, etc.
3. Remove what you don't need
4. Add the AWS secrets in GHA if needed
5. Add the Sonar project to their UI if needed
6. Uncomment the stages in GHA for PR and Main files as needed
7. Look for this `changeme` with a global find
8. Change it to what it should be
9. Look for `shells.springboot` with a global find
10. Change it to what it should be
11. Look for `springboot-service` with a global find
12. Change it to what it should be
13. Look for `shell` with a global find. Make sure it is not case sensitive
14. Change it to what it should be
15. Run `mvnci` from terminal and make sure it passes
16. Open in IntelliJ
17. Turn on Save Actions
18. Check application starts with `-Dspring.profiles.active=local`
