PRAGMA foreign_keys = false;
CREATE TABLE "FilePermission" (
                                  "id" INTEGER NOT NULL,
                                  "bucket_id" text,
                                  "path" text,
                                  "password" text,
                                  "url" text,
                                  "limit_ip" TEXT,
                                  PRIMARY KEY ("id")
);

PRAGMA foreign_keys = true;