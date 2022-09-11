/*
 Navicat Premium Data Transfer

 Source Server         : Zcloud
 Source Server Type    : SQLite
 Source Server Version : 3030001
 Source Schema         : main

 Target Server Type    : SQLite
 Target Server Version : 3030001
 File Encoding         : 65001

 Date: 28/08/2022 11:59:35
*/

PRAGMA foreign_keys = false;


DROP TABLE IF EXISTS "FilePermission";
CREATE TABLE "FilePermission" (
                                  "id" INTEGER NOT NULL,
                                  "user_id" INTEGER,
                                  "bucket_id" text,
                                  "path" text,
                                  "password" text,
                                  "url" text,
                                  "limit_ip" TEXT,
                                  PRIMARY KEY ("id"),
                                  CONSTRAINT "userid" UNIQUE ("user_id" COLLATE BINARY ASC),
                                  CONSTRAINT "id" UNIQUE ("id" COLLATE BINARY ASC)
);


DROP TABLE IF EXISTS "User";
CREATE TABLE "User" (
                        "id" integer NOT NULL,
                        "username" TEXT,
                        "password" TEXT,
                        "type" TEXT,
                        "deleted" integer,
                        PRIMARY KEY ("id")
);

-- ----------------------------
-- Table structure for UserFilePermission
-- ----------------------------
DROP TABLE IF EXISTS "UserFilePermission";
CREATE TABLE "UserFilePermission" (
                                      "id" integer NOT NULL,
                                      "user_id" INTEGER,
                                      "path" TEXT,
                                      "permission" TEXT,
                                      "limit_ip" TEXT,
                                      "deleted" integer,
                                      PRIMARY KEY ("id"),
                                      CONSTRAINT "fileuserid" UNIQUE ("user_id" COLLATE BINARY ASC),
                                      CONSTRAINT "fileid" UNIQUE ("id" COLLATE BINARY ASC)
);

-- ----------------------------
-- Table structure for CommomConfig
-- ----------------------------
DROP TABLE IF EXISTS "CommonConfig";
CREATE TABLE "CommonConfig" (
                                "id" INTEGER NOT NULL,
                                "config_name" TEXT,
                                "config_value" TEXT,
                                PRIMARY KEY ("id")
);

-- ----------------------------
-- Indexes structure for table CommomConfig
-- ----------------------------
CREATE UNIQUE INDEX "config_name_unique"
    ON "CommonConfig" (
                       "config_name" COLLATE BINARY ASC
        );



PRAGMA foreign_keys = true;


INSERT INTO "main"."User"("id", "username", "password", "type", "deleted") VALUES (1563729771781799937, 'admin', 'admin', 'admin', 0);
