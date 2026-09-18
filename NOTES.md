# NOTES

## Summary of changes

1. **SQL precedence:** `AND` bound tighter than `OR`, so the status filter was ignored and archived tasks leaked. Fixed in both `db/` files too; added `id` tiebreaker.
2. **Stale search results:** older, slower responses overwrote newer ones; fixed with `AbortController`.
3. **Loading/error state:** errors showed "Loading…" forever and never cleared.
4. **Pagination dead end:** page not reset on search/filter change.
5. **Artificial `Thread.sleep`:** 1s per blank search; thread-exhaustion DoS vector.
6. **Input validation:** bad `status`/`page`/`pageSize` return 400, not 500; `pageSize` max 100.
7. **Log injection (CWE-117):** raw `q` in `System.out` replaced by SLF4J, control chars stripped.
8. **Response DTO:** stop serializing the JPA entity (OWASP API3).
9. **Dependencies + CI:** Vite `^6.4.3`: lowest version clearing all npm advisories while keeping Node 18. CI with SHA-pinned actions, read-only token, `npm audit` gate.

Handwritten notes (`handwritten/`) cover fixes 1–6; 7–9 are summarised above due to time.

## Assumptions

- Archived = soft-deleted; never shown in search.
- Search and status filter combine with AND.
- `pageSize` max 100 is enough (UI uses 10).
- H2 console is a local dev tool.

## Not changed

- **H2 console:** README relies on it; localhost-only by default. Belongs in a `dev` profile.
- **In-memory pagination:** fine for 49 rows; needs DB-side `LIMIT/OFFSET`.
- **Unescaped LIKE wildcards:** low impact; queries stay parameterized.
- **Spring Boot 3.2.5 (past OSS support):** upgrade too broad for a patch.

## Biggest remaining risk

No authentication or authorization, and the API listens on `0.0.0.0:8080`: anyone on the network can read every task.

## Tools / AI

Claude: review, repro scripts checked against `data.sql`, browser tests, zizmor
