set foreign_key_checks = 0;

set @DUMMY='dummy';
set @DUMMY_ID=0;
set @CREATED_BY='system';
set @UPDATED_BY='system';
set @PASSWORD_PASS='$2a$10$5MrI6YjjaM9ZbjfWJJL6P.k6iyAPv32EhgTCyX.0CvYp7F/nrVJOW'; /* pass */
set @ENABLE_FLAG_ON=1;
set @ENABLE_FLAG_OFF=0;

/*---------------------------------------------------------------------------*/
truncate table `payment_type`;
INSERT INTO `payment_type` (`ID`, `NAME`, `LOGO`, `ENABLE_FLAG`, `CREATED_BY`, `CREATED_DATETIME`, `UPDATED_BY`, `UPDATED_DATETIME`) VALUES
( 1, 'クレジット', '', 1, 'system', '2019-02-22 17:03:19', 'system', '2019-02-22 17:03:19'),
( 2, 'LINE Pay', '', 1, 'system', '2019-02-22 17:03:19', 'system', '2019-02-22 17:03:19'),
( 3, 'Google Pay', '', 1, 'system', '2019-02-22 17:03:19', 'system', '2019-02-22 17:03:19'),
( 4, 'Apple Pay', '', 1, 'system', '2019-02-22 17:03:19', 'system', '2019-02-22 17:03:19'),
( 5, '楽天ペイ', '', 1, 'system', '2019-02-22 17:03:19', 'system', '2019-02-22 17:03:19'),
( 6, 'Alipay', '', 1, 'system', '2019-02-22 17:03:19', 'system', '2019-02-22 17:03:19'),
( 7, '中国銀聯', '', 1, 'system', '2019-02-22 17:03:19', 'system', '2019-02-22 17:03:19'),

(@DUMMY_ID, 'xxx', 'xxx', @ENABLE_FLAG_OFF, @DUMMY, CURRENT_TIMESTAMP, @UPDATED_BY, CURRENT_TIMESTAMP) /*EOF*/
;
delete from `payment_type` where `CREATED_BY` = @DUMMY;
alter table `payment_type` auto_increment=1;

/*---------------------------------------------------------------------------*/
truncate table `system_property`;
INSERT INTO `system_property` (`ID`, `CODE`, `VALUE`, `REMARKS`, `CREATED_BY`, `CREATED_DATETIME`, `UPDATED_BY`, `UPDATED_DATETIME`) VALUES
(1, 'COMPANY_RUNNING', '稼働中', 'system', 'system', '2019-02-22 17:03:19', 'system', '2019-02-22 17:03:19'),
(2, 'COMPANY_STOP_OPERATION', '稼働停止', 'system', 'system', '2019-02-22 17:03:19', 'system', '2019-02-22 17:03:19'),
(11, 'SHOP_RUNNING', '1', 'system', 'system', '2019-02-22 17:03:19', 'system', '2019-02-22 17:03:19'),
(12, 'SHOP_STOP_OPERATION', '0', 'system', 'system', '2019-02-22 17:03:19', 'system', '2019-02-22 17:03:19'),
(22, 'QR_CODE_TIMER', '86400', 'system', 'system', '2019-02-22 17:03:19', 'system', '2019-02-22 17:03:19'),

(@DUMMY_ID, 'xxx', '', '', @DUMMY, CURRENT_TIMESTAMP, @UPDATED_BY, CURRENT_TIMESTAMP) /*EOF*/
;
delete from `system_property` where `CREATED_BY` = @DUMMY;
alter table `system_property` auto_increment=1;

/*---------------------------------------------------------------------------*/
truncate table `role`;
INSERT INTO `role` (`ID`, `NAME`, `ENABLE_FLAG`, `CREATED_BY`, `CREATED_DATETIME`, `UPDATED_BY`, `UPDATED_DATETIME`) VALUES
(1, 'TECステアリングメンバー', 1, 'system', '2019-02-22 17:03:19', 'system', '2019-02-22 17:03:19'),
(11, 'TECグループ運用・保守メンバー', 1, 'system', '2019-02-22 17:03:19', 'system', '2019-02-22 17:03:19'),
(21, '加盟店様メンバー', 1, 'system', '2019-02-22 17:03:19', 'system', '2019-02-22 17:03:19'),

(@DUMMY_ID, @DUMMY, @ENABLE_FLAG_OFF, @DUMMY, CURRENT_TIMESTAMP, @UPDATED_BY, CURRENT_TIMESTAMP) /*EOF*/
;
delete from `role` where `CREATED_BY` = @DUMMY;
alter table `role` auto_increment=1;

/*---------------------------------------------------------------------------*/
truncate table `role_authority`;
INSERT INTO `role_authority` (`ID`, `CODE`, `SORT`, `OVERVIEW`, `DESCRIPTION`, `ENABLE_FLAG`, `CREATED_BY`, `CREATED_DATETIME`, `UPDATED_BY`, `UPDATED_DATETIME`) VALUES
(1, 'COMPANY_INDEX_VIEW', 101, '加盟店画面 - トップ', '', 1, 'system', '2019-02-22 17:03:19', 'system', '2019-02-22 17:03:19'),
(2, 'COMPANY_EDIT_VIEW', 101, '加盟店画面 - 編集', '', 1, 'system', '2019-02-22 17:03:19', 'system', '2019-02-22 17:03:19'),
(3, 'COMPANY_DELETE_VIEW', 101, '加盟店画面 - 削除', '', 1, 'system', '2019-02-22 17:03:19', 'system', '2019-02-22 17:03:19'),
(4, 'SHOP_INDEX_VIEW', 101, '店舗画面 - トップ', '', 1, 'system', '2019-02-22 17:03:19', 'system', '2019-02-22 17:03:19'),
(5, 'SHOP_EDIT_VIEW', 101, '店舗画面 - 編集', '', 1, 'system', '2019-02-22 17:03:19', 'system', '2019-02-22 17:03:19'),
(6, 'SHOP_DELETE_VIEW', 101, '店舗画面 - 削除', '', 1, 'system', '2019-02-22 17:03:19', 'system', '2019-02-22 17:03:19'),
(7, 'USER_MAINTENANCE_INDEX_VIEW', 101, 'ユーザー管理画面 - トップ', '', 1, 'system', '2019-02-22 17:03:19', 'system', '2019-02-22 17:03:19'),
(8, 'USER_MAINTENANCE_EDIT_VIEW', 101, 'ユーザー管理画面 - 編集', '', 1, 'system', '2019-02-22 17:03:19', 'system', '2019-02-22 17:03:19'),
(9, 'USER_MAINTENANCE_DELETE_VIEW', 101, 'ユーザー管理画面 - 削除', '', 1, 'system', '2019-02-22 17:03:19', 'system', '2019-02-22 17:03:19'),

(@DUMMY_ID, 'xxx', 000, '', '', @ENABLE_FLAG_OFF, @DUMMY, CURRENT_TIMESTAMP, @UPDATED_BY, CURRENT_TIMESTAMP) /*EOF*/
;
delete from `role_authority` where `CREATED_BY` = @DUMMY;
alter table `role_authority` auto_increment=1;

/*---------------------------------------------------------------------------*/
truncate table `merchant_company`;
INSERT INTO `merchant_company` (`ID`, `MID`, `MALLMAP_ID`, `CCID`, `CERTIFICATION_KEY`, `COMPANY_NAME`, `MAIN_PHONE_NUMBER`, `MAIN_ADDRESS`, `REPRESENTATIVE_NAME`, `EMAIL`, `RUNNING_STATUS`, `ENABLE_FLAG`, `CREATED_BY`, `CREATED_DATETIME`, `UPDATED_BY`, `UPDATED_DATETIME`) VALUES
(1, 'TECSYSTEM2019123456789', 'MAP_1', 'CCID_1', 'KEY1', '(株)会社_A', '0801', '東京都港区東町7', '代表太郎', 'company1@garage.test', '稼働中', 1, 'system', '2019-02-22 17:03:19', 'system', '2019-02-22 17:03:19'),
(2, 'TECADMIN20191234567890', 'MAP_1', 'CCID_2', 'KEY2', '(株)会社_B', '0902', '東京都港区東町8', '代表次郎', 'company2@garage.test', '稼働中', 1, 'system', '2019-02-22 17:03:19', 'system', '2019-02-22 17:03:19'),
(3, 'TECUSER201912345678901', 'MAP_1', 'CCID_3', 'KEY3', '(株)会社_C', '0703', '東京都港区東町9', '代表三郎', 'company3@garage.test', '稼働中', 1, 'system', '2019-02-22 17:03:19', 'system', '2019-02-22 17:03:19'),
(4, 'TECUSER201912345678902', 'MAP_11', 'CCID_11', 'KEY4', '(株)会社_A1', '08011', '東京都港区東町17', '代表太郎丸', 'company4@garage.test', '稼働中', 1, 'system', '2019-02-22 17:03:19', 'system', '2019-02-22 17:03:19'),
(5, 'TECUSER201912345678903', 'MAP_11', 'CCID_12', 'KEY5', '(株)会社_B1', '09012', '東京都港区東町18', '代表次郎丸', 'company5@garage.test', '稼働中', 1, 'system', '2019-02-22 17:03:19', 'system', '2019-02-22 17:03:19'),
(6, 'TTSS201906112345678901', 'MAP_11', 'CCID_13', 'KEY6', '(株)会社_C1', '07013', '東京都港区東町19', '代表三郎丸', 'company6@garage.test', '稼働中', 1, 'system', '2019-02-22 17:03:19', 'system', '2019-02-22 17:03:19'),
(7, 'M123456789012345678901', 'MAP_21', 'CCID_21', 'KEY7', '(株)会社_A2', '08021', '東京都港区西町27', '代表考太郎', 'company7@garage.test', '稼働中', 1, 'system', '2019-02-22 17:03:19', 'system', '2019-02-22 17:03:19'),
(8, 'M123456789012345678902', 'MAP_21', 'CCID_22', 'KEY8', '(株)会社_B2', '09022', '東京都港区西町28', '代表考次郎', 'company8@garage.test', '稼働中', 1, 'system', '2019-02-22 17:03:19', 'system', '2019-02-22 17:03:19'),
(9, 'M123456789012345678903', 'MAP_21', 'CCID_23', 'KEY9', '(株)会社_C2', '07023', '東京都港区西町29', '代表考三郎', 'company9@garage.test', '稼働中', 1, 'system', '2019-02-22 17:03:19', 'system', '2019-02-22 17:03:19'),

(@DUMMY_ID, 'xxx', 'xxx', 'xxx', 'xxx', '', '', '', '', '', '', @ENABLE_FLAG_OFF, @DUMMY, CURRENT_TIMESTAMP, @UPDATED_BY, CURRENT_TIMESTAMP) /*EOF*/
;
delete from `merchant_company` where `CREATED_BY` = @DUMMY;
alter table `merchant_company` auto_increment=1;

/*---------------------------------------------------------------------------*/
truncate table `merchant_shop`;
INSERT INTO `merchant_shop` (`ID`, `MERCHANT_COMPANY_ID`, `EC_SHOP_CODE`, `SHOP_NAME`, `SHOP_PHONE_NUMBER`, `SHOP_ADDRESS`, `SHOP_MANAGER_NAME`, `MERCHANT_PAYMENT_TYPE_ID`, `ANY_MESSAGE`, `SALT_KEY`, `RUNNING_STATUS`, `ENABLE_FLAG`, `CREATED_BY`, `CREATED_DATETIME`, `UPDATED_BY`, `UPDATED_DATETIME`) VALUES
( 1, 1, 'SHOP_CODE_1',  'SHOP_NAME_1', 'TENPO_1',    '東京都港区南町7', '店舗太郎', 1,    'system', '0801', '稼働中', 1, 'system', '2019-02-22 17:03:19', 'system', '2019-02-22 17:03:19'),
( 2, 1, 'SHOP_CODE_2',  'SHOP_NAME_2', 'TENPO_2',    '東京都港区北町8', '店舗次郎', 2,    'system', '0902', '稼働中', 1, 'system', '2019-02-22 17:03:19', 'system', '2019-02-22 17:03:19'),
( 3, 1, 'SHOP_CODE_3',  'SHOP_NAME_3', 'TENPO_3',    '東京都港区北町9', '店舗三郎', 3,    'system', '0703', '稼働中', 1, 'system', '2019-02-22 17:03:19', 'system', '2019-02-22 17:03:19'),
( 4, 1, 'SHOP_CODE_4',  'SHOP_NAME_4', 'TENPO_3',    '東京都港区北町1', '店舗四郎', 4,    'system', '0103', '稼働中', 1, 'system', '2019-02-22 17:03:19', 'system', '2019-02-22 17:03:19'),
( 5, 1, 'SHOP_CODE_5',  'SHOP_NAME_5', 'TENPO_3',    '東京都港区北町2', '店舗五郎', 5,    'system', '0203', '稼働中', 1, 'system', '2019-02-22 17:03:19', 'system', '2019-02-22 17:03:19'),
( 6, 1, 'SHOP_CODE_6',  'SHOP_NAME_6', 'TENPO_3',    '東京都港区北町3', '店舗六郎', 6,    'system', '0303', '稼働中', 1, 'system', '2019-02-22 17:03:19', 'system', '2019-02-22 17:03:19'),
( 7, 1, 'SHOP_CODE_7',  'SHOP_NAME_7', 'TENPO_4',    '東京都港区北町4', '店舗七郎', 7,    'system', '0403', '稼働中', 1, 'system', '2019-02-22 17:03:19', 'system', '2019-02-22 17:03:19'),
( 8, 2, 'SHOP_CODE_8',  'SHOP_NAME_8', 'TENPO_11',   '東京都港区南町17', '店舗太郎丸', 1, 'system', '0801', '稼働中', 1, 'system', '2019-02-22 17:03:19', 'system', '2019-02-22 17:03:19'),
( 9, 2, 'SHOP_CODE_9',  'SHOP_NAME_9', 'TENPO_12',   '東京都港区北町18', '店舗次郎丸', 2, 'system', '0901', '稼働中', 1, 'system', '2019-02-22 17:03:19', 'system', '2019-02-22 17:03:19'),
(10, 2, 'SHOP_CODE_10', 'SHOP_NAME_10', 'TENPO_13', '東京都港区北町19', '店舗三郎丸', 3, 'system', '0701', '稼働中', 1, 'system', '2019-02-22 17:03:19', 'system', '2019-02-22 17:03:19'),
(11, 2, 'SHOP_CODE_11', 'SHOP_NAME_11', 'TENPO_14', '東京都港区北町11', '店舗四郎丸', 4, 'system', '0101', '稼働中', 1, 'system', '2019-02-22 17:03:19', 'system', '2019-02-22 17:03:19'),
(12, 2, 'SHOP_CODE_12', 'SHOP_NAME_12', 'TENPO_15', '東京都港区北町12', '店舗五郎丸', 5, 'system', '0201', '稼働中', 1, 'system', '2019-02-22 17:03:19', 'system', '2019-02-22 17:03:19'),
(13, 3, 'SHOP_CODE_21', 'SHOP_NAME_21', 'TENPO_21', '東京都港区南町27', '店舗考太郎', 1, 'system', '0802', '稼働中', 1, 'system', '2019-02-22 17:03:19', 'system', '2019-02-22 17:03:19'),
(14, 3, 'SHOP_CODE_22', 'SHOP_NAME_22', 'TENPO_22', '東京都港区北町28', '店舗考次郎', 2, 'system', '0902', '稼働中', 1, 'system', '2019-02-22 17:03:19', 'system', '2019-02-22 17:03:19'),
(15, 3, 'SHOP_CODE_23', 'SHOP_NAME_23', 'TENPO_23', '東京都港区北町29', '店舗考三郎', 3, 'system', '0702', '稼働中', 1, 'system', '2019-02-22 17:03:19', 'system', '2019-02-22 17:03:19'),

(@DUMMY_ID, @DUMMY_ID, 'xxx', 'xxx', 'xxx', '', 'xxx', @DUMMY_ID, 'xxx', 'xxx', 'xxx', @ENABLE_FLAG_OFF, @DUMMY, CURRENT_TIMESTAMP, @UPDATED_BY, CURRENT_TIMESTAMP) /*EOF*/
;
delete from `merchant_shop` where `CREATED_BY` = @DUMMY;
alter table `merchant_shop` auto_increment=1;

/*---------------------------------------------------------------------------*/
truncate table `merchant_payment_type`;
INSERT INTO `merchant_payment_type` (`ID`, `MERCHANT_COMPANY_ID`, `MERCHANT_SHOP_ID`, `PAYMENT_TYPE_ID`, `START_DATETIME`, `END_DATETIME`, `ENABLE_FLAG`, `CREATED_BY`, `CREATED_DATETIME`, `UPDATED_BY`, `UPDATED_DATETIME`) VALUES
( 1, 1, null, 1, '2019-02-22 17:03:19', null, 1, 'system', '2019-02-22 17:03:19', 'system', '2019-02-22 17:03:19'),
( 2, 1, null, 2, '2019-02-22 17:03:19', null, 1, 'system', '2019-02-22 17:03:19', 'system', '2019-02-22 17:03:19'),
( 3, 1, null, 3, '2019-02-22 17:03:19', null, 1, 'system', '2019-02-22 17:03:19', 'system', '2019-02-22 17:03:19'),
( 4, 1, null, 4, '2019-02-22 17:03:19', null, 1, 'system', '2019-02-22 17:03:19', 'system', '2019-02-22 17:03:19'),
( 5, 1, null, 5, '2019-02-22 17:03:19', null, 1, 'system', '2019-02-22 17:03:19', 'system', '2019-02-22 17:03:19'),
( 6, 1, null, 6, '2019-02-22 17:03:19', null, 1, 'system', '2019-02-22 17:03:19', 'system', '2019-02-22 17:03:19'),
( 7, 1, null, 7, '2019-02-22 17:03:19', null, 1, 'system', '2019-02-22 17:03:19', 'system', '2019-02-22 17:03:19'),
(11, 2, 1, 1, '2019-02-22 17:03:19', null, 1, 'system', '2019-02-22 17:03:19', 'system', '2019-02-22 17:03:19'),
(12, 3, 1, 2, '2019-02-22 17:03:19', null, 1, 'system', '2019-02-22 17:03:19', 'system', '2019-02-22 17:03:19'),
(13, 3, 2, 3, '2019-02-22 17:03:19', null, 1, 'system', '2019-02-22 17:03:19', 'system', '2019-02-22 17:03:19'),
(14, 3, 3, 4, '2019-02-22 17:03:19', null, 1, 'system', '2019-02-22 17:03:19', 'system', '2019-02-22 17:03:19'),
(15, 4, 1, 5, '2019-02-22 17:03:19', null, 1, 'system', '2019-02-22 17:03:19', 'system', '2019-02-22 17:03:19'),
(21, 4, 2, 1, '2019-02-22 17:03:19', null, 1, 'system', '2019-02-22 17:03:19', 'system', '2019-02-22 17:03:19'),
(22, 4, 3, 2, '2019-02-22 17:03:19', null, 1, 'system', '2019-02-22 17:03:19', 'system', '2019-02-22 17:03:19'),
(23, 4, 4, 3, '2019-02-22 17:03:19', null, 1, 'system', '2019-02-22 17:03:19', 'system', '2019-02-22 17:03:19'),
(24, 4, 5, 4, '2019-02-22 17:03:19', null, 1, 'system', '2019-02-22 17:03:19', 'system', '2019-02-22 17:03:19'),
(31, 4, 6, 1, '2019-02-22 17:03:19', null, 1, 'system', '2019-02-22 17:03:19', 'system', '2019-02-22 17:03:19'),

(@DUMMY_ID, @DUMMY_ID, @DUMMY_ID, @DUMMY_ID, CURRENT_TIMESTAMP, null, @ENABLE_FLAG_OFF, @DUMMY, CURRENT_TIMESTAMP, @UPDATED_BY, CURRENT_TIMESTAMP) /*EOF*/
;
delete from `merchant_payment_type` where `CREATED_BY` = @DUMMY;
alter table `merchant_payment_type` auto_increment=1;

/*---------------------------------------------------------------------------*/
truncate table `user`;
INSERT INTO `user` (`ID`, `ROLE_ID`, `MID`, `LOGIN_ID`, `PASSWORD`, `PASSWORD_INITIALIZE_FLAG`, `NAME`, `EMAIL`, `ENABLE_FLAG`, `CREATED_BY`, `CREATED_DATETIME`, `UPDATED_BY`, `UPDATED_DATETIME`) VALUES
(1, 1, 'TECUSER201912345678901', 'tecuser1@garage.test', @PASSWORD_PASS, 0, 'TEC ユーザー1', 'tecuser1@garage.test', 1, 'system', '2019-02-22 17:03:19', 'system', '2019-02-22 17:03:19'),
(2, 1, 'TECUSER201912345678902', 'tecuser2@garage.test', @PASSWORD_PASS, 0, 'TEC ユーザー2', 'tecuser2@garage.test', 1, 'system', '2019-02-22 17:03:19', 'system', '2019-02-22 17:03:19'),
(3, 1, 'TECUSER201912345678903', 'tecuser3@garage.test', @PASSWORD_PASS, 0, 'TEC ユーザー3', 'tecuser3@garage.test', 1, 'system', '2019-02-22 17:03:19', 'system', '2019-02-22 17:03:19'),
(4, 11, 'TTSS201906112345678901', 'ttss1@garage.test', @PASSWORD_PASS, 0, 'TTSS ユーザー1', 'ttss1@garage.test', 1, 'system', '2019-02-22 17:03:19', 'system', '2019-02-22 17:03:19'),
(5, 11, 'TTSS201906112345678902', 'ttss2@garage.test', @PASSWORD_PASS, 0, 'TTSS ユーザー2', 'ttss2@garage.test', 1, 'system', '2019-02-22 17:03:19', 'system', '2019-02-22 17:03:19'),
(6, 11, 'TTSS201906112345678902', 'ttss3@garage.test', @PASSWORD_PASS, 0, 'TTSS ユーザー3', 'ttss2@garage.test', 1, 'system', '2019-02-22 17:03:19', 'system', '2019-02-22 17:03:19'),
(7, 21, 'M123456789012345678901', 'm1@garage.test', @PASSWORD_PASS, 0, '加盟店 ユーザー1', 'm1@garage.test', 1, 'system', '2019-02-22 17:03:19', 'system', '2019-02-22 17:03:19'),
(8, 21, 'M123456789012345678902', 'm2@garage.test', @PASSWORD_PASS, 0, '加盟店 ユーザー2', 'm2@garage.test', 1, 'system', '2019-02-22 17:03:19', 'system', '2019-02-22 17:03:19'),
(9, 21, 'M123456789012345678903', 'm3@garage.test', @PASSWORD_PASS, 1, '加盟店 ユーザー3', 'm3@garage.test', 1, 'system', '2019-02-22 17:03:19', 'system', '2019-02-22 17:03:19'),

(@DUMMY_ID, @DUMMY_ID, 'xxx', 'xxx', @PASSWORD_PASS, 0, '', '', @ENABLE_FLAG_OFF, @DUMMY, CURRENT_TIMESTAMP, @UPDATED_BY, CURRENT_TIMESTAMP) /*EOF*/
;
delete from `user` where `CREATED_BY` = @DUMMY;
alter table `user` auto_increment=1;

/*---------------------------------------------------------------------------*/
truncate table `user_authority`;
INSERT INTO `user_authority` (`ID`, `ROLE_ID`, `CODE`, `ENABLE_FLAG`, `CREATED_BY`, `CREATED_DATETIME`, `UPDATED_BY`, `UPDATED_DATETIME`) VALUES
(1, 1, 'COMPANY_INDEX_VIEW', 1, 'system', '2019-02-22 17:03:19', 'system', '2019-02-22 17:03:19'),
(2, 1, 'COMPANY_EDIT_VIEW', 1, 'system', '2019-02-22 17:03:19', 'system', '2019-02-22 17:03:19'),
(3, 1, 'COMPANY_DELETE_VIEW', 1, 'system', '2019-02-22 17:03:19', 'system', '2019-02-22 17:03:19'),
(4, 1, 'SHOP_INDEX_VIEW', 1, 'system', '2019-02-22 17:03:19', 'system', '2019-02-22 17:03:19'),
(5, 1, 'SHOP_EDIT_VIEW', 1, 'system', '2019-02-22 17:03:19', 'system', '2019-02-22 17:03:19'),
(6, 1, 'SHOP_DELETE_VIEW', 1, 'system', '2019-02-22 17:03:19', 'system', '2019-02-22 17:03:19'),
(7, 1, 'USER_MAINTENANCE_INDEX_VIEW', 1, 'system', '2019-02-22 17:03:19', 'system', '2019-02-22 17:03:19'),
(8, 1, 'USER_MAINTENANCE_EDIT_VIEW', 1, 'system', '2019-02-22 17:03:19', 'system', '2019-02-22 17:03:19'),
(9, 1, 'USER_MAINTENANCE_DELETE_VIEW', 1, 'system', '2019-02-22 17:03:19', 'system', '2019-02-22 17:03:19'),
(10, 11, 'COMPANY_INDEX_VIEW', 1, 'system', '2019-02-22 17:03:19', 'system', '2019-02-22 17:03:19'),
(11, 11, 'SHOP_INDEX_VIEW', 1, 'system', '2019-02-22 17:03:19', 'system', '2019-02-22 17:03:19'),
(12, 11, 'SHOP_EDIT_VIEW', 1, 'system', '2019-02-22 17:03:19', 'system', '2019-02-22 17:03:19'),
(13, 21, 'COMPANY_INDEX_VIEW', 1, 'system', '2019-02-22 17:03:19', 'system', '2019-02-22 17:03:19'),
(14, 21, 'SHOP_INDEX_VIEW', 1, 'system', '2019-02-22 17:03:19', 'system', '2019-02-22 17:03:19'),
(15, 21, 'SHOP_EDIT_VIEW', 1, 'system', '2019-02-22 17:03:19', 'system', '2019-02-22 17:03:19'),

(@DUMMY_ID, @DUMMY_ID, 'xxx', @ENABLE_FLAG_OFF, @DUMMY, CURRENT_TIMESTAMP, @UPDATED_BY, CURRENT_TIMESTAMP) /*EOF*/
;
delete from `user_authority` where `CREATED_BY` = @DUMMY;
alter table `user_authority` auto_increment=1;

set foreign_key_checks = 1;