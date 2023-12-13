/*
  Warnings:

  - You are about to drop the column `type_id` on the `user_recommendation` table. All the data in the column will be lost.
  - Added the required column `place_type_id` to the `user_recommendation` table without a default value. This is not possible if the table is not empty.

*/
-- AlterTable
ALTER TABLE `user_recommendation` DROP COLUMN `type_id`,
    ADD COLUMN `place_type_id` VARCHAR(191) NOT NULL;
