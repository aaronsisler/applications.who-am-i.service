# App User module: hexagonal implications

- **Use cases:** `UpdateAppUserCommand` and `delete(AppUserId)` join `create` and `get` on
  the inbound port.
- **Search as a query, not a command.** A `SearchAppUsersQuery` record (filters, page,
  size, sort) returns our own small `Page<AppUserView>` record in the application layer.
  Spring Data's `Page` would bring Spring into the application layer, which the ArchUnit
  rules forbid.
- **A separate read port.** The repository port stays focused on saving and loading single
  users for the domain. A second outbound port, such as `AppUserSearch`, runs the list
  query straight into views: the read side of CQRS.
- **Sort mapped through a whitelist.** The adapter translates each allowed sort value to a
  fixed SQL `order by` clause. Never insert the request value into SQL.
- **The email uniqueness check on update must exclude the user being updated**, the same
  pattern as the product name check.
- **Delete needs two decisions:**
  - Hard or soft delete. A soft delete keeps the row with a `deleted_at` timestamp, which
    matters for audits and personal-data obligations.
  - Whether other modules need to know, which would mean an `AppUserDeleted` event in the
    module's `api` package.
- **Pagination totals cost a second query** (`count(*)` with the same filters). Fine at
  this scale; the first thing to revisit if listing gets slow.

@Mapper
interface AppUserWebMapper extends CommonWebMappings {

CreateAppUserCommand toCommand(CreateAppUserRequest request);

@Mapping(target = "id", source = "id.value")
AppUserResponse toResponse(AppUserView view);

default String toString(EmailAddress emailAddress) {
return emailAddress.value();
}
}
