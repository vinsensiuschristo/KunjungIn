-- AlterTable
ALTER TABLE `user` ADD COLUMN `city_id` INTEGER NOT NULL DEFAULT 1;

-- AddForeignKey
ALTER TABLE `user` ADD CONSTRAINT `user_city_id_fkey` FOREIGN KEY (`city_id`) REFERENCES `city`(`id`) ON DELETE RESTRICT ON UPDATE CASCADE;
