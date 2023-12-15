/*
  Warnings:

  - Added the required column `photo_reference` to the `historical_place` table without a default value. This is not possible if the table is not empty.

*/
-- AlterTable
ALTER TABLE `historical_place` ADD COLUMN `photo_reference` TEXT NOT NULL;
