DO $$
DECLARE
   db_creator TEXT := 'auth_database_creation_user';
   ide_user   TEXT := 'auth_ide_user';
   app_user   TEXT := 'auth_app_user';
BEGIN
   IF NOT EXISTS (SELECT FROM pg_roles WHERE rolname = db_creator) THEN
      EXECUTE 'CREATE ROLE ' || quote_ident(db_creator) || ' LOGIN PASSWORD ''CHANGE_ME''';
   END IF;

   IF NOT EXISTS (SELECT FROM pg_roles WHERE rolname = ide_user) THEN
      EXECUTE 'CREATE ROLE ' || quote_ident(ide_user) || ' LOGIN PASSWORD ''CHANGE_ME''';
   END IF;

   IF NOT EXISTS (SELECT FROM pg_roles WHERE rolname = app_user) THEN
      EXECUTE 'CREATE ROLE ' || quote_ident(app_user) || ' LOGIN PASSWORD ''CHANGE_ME''';
   END IF;

   EXECUTE 'GRANT ' || quote_ident(ide_user) || ' TO ' || quote_ident(db_creator);
END
$$;