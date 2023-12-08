/*
  Warnings:

  - A unique constraint covering the columns `[user_id]` on the table `user` will be added. If there are existing duplicate values, this will fail.

*/
-- AlterTable
ALTER TABLE `user` ADD COLUMN `user_id` VARCHAR(191) NOT NULL DEFAULT 'temp_default_value';

-- CreateIndex
CREATE UNIQUE INDEX `user_user_id_key` ON `user`(`user_id`);
