-- ログコード
drop table if exists `log_code` cascade;

create table `log_code` (
  `ID` BIGINT not null auto_increment comment 'ID'
  , `CODE` VARCHAR(256) not null comment 'コード'
  , `MESSAGE` VARCHAR(2000) comment 'メッセージ'
  , `ENABLE_FLAG` INT default 1 not null comment '有効フラグ'
  , `CREATED_BY` VARCHAR(256) not null comment '作成者'
  , `CREATED_DATETIME` DATETIME not null comment '作成日時'
  , `UPDATED_BY` VARCHAR(256) comment '更新者'
  , `UPDATED_DATETIME` DATETIME comment '更新日時'
  , constraint `log_code_PKC` primary key (`ID`)
) comment 'ログコード' ;

alter table `log_code` add unique `log_code_IX1` (`CODE`) ;

-- ユーザー情報
drop table if exists `user` cascade;

create table `user` (
  `ID` BIGINT not null auto_increment comment 'ID'
  , `ROLE_ID` BIGINT not null comment 'ロールID'
  , `MID` VARCHAR(30) not null comment 'MID'
  , `LOGIN_ID` VARCHAR(256) not null comment 'ログインID'
  , `NAME` VARCHAR(200) not null comment '名前'
  , `PASSWORD` VARCHAR(256) not null comment 'パスワード'
  , `EMAIL` VARCHAR(256) comment 'メールアドレス'
  , `ENABLE_FLAG` INT not null comment '有効フラグ'
  , `CREATED_BY` VARCHAR(256) not null comment '作成者'
  , `CREATED_DATETIME` DATETIME not null comment '作成日時'
  , `UPDATED_BY` VARCHAR(256) comment '更新者'
  , `UPDATED_DATETIME` DATETIME comment '更新日時'
  , constraint `user_PKC` primary key (`ID`)
) comment 'ユーザー情報' ;

alter table `user` add unique `user_IX1` (`MID`,`LOGIN_ID`,`ROLE_ID`) ;

-- ロール
drop table if exists `role` cascade;

create table `role` (
  `ID` BIGINT not null auto_increment comment 'ID'
  , `NAME` VARCHAR(200) not null comment 'ロール名'
  , `ENABLE_FLAG` INT not null comment '有効フラグ'
  , `CREATED_BY` VARCHAR(256) not null comment '作成者'
  , `CREATED_DATETIME` DATETIME not null comment '作成日時'
  , `UPDATED_BY` VARCHAR(256) comment '更新者'
  , `UPDATED_DATETIME` DATETIME comment '更新日時'
  , constraint `role_PKC` primary key (`ID`)
) comment 'ロール' ;

-- 加盟店-決済情報
drop table if exists `merchant_payment_type` cascade;

create table `merchant_payment_type` (
  `ID` BIGINT not null auto_increment comment 'ID'
  , `MERCHANT_COMPANY_ID` BIGINT not null comment '加盟店ID'
  , `MERCHANT_SHOP_ID` BIGINT comment '店舗ID'
  , `PAYMENT_TYPE_ID` BIGINT not null comment '決済種別ID'
  , `START_DATETIME` DATETIME not null comment '利用開始日'
  , `END_DATETIME` DATETIME comment '利用停止日'
  , `ENABLE_FLAG` INT not null comment '有効フラグ'
  , `CREATED_BY` VARCHAR(256) not null comment '作成者'
  , `CREATED_DATETIME` DATETIME not null comment '作成日時'
  , `UPDATED_BY` VARCHAR(256) comment '更新者'
  , `UPDATED_DATETIME` DATETIME comment '更新日時'
  , constraint `merchant_payment_type_PKC` primary key (`ID`)
) comment '加盟店-決済情報' ;

alter table `merchant_payment_type` add unique `merchant_payment_type_IX1` (`MERCHANT_COMPANY_ID`, `MERCHANT_SHOP_ID`,`PAYMENT_TYPE_ID`) ;

-- システムプロパティ
drop table if exists `system_property` cascade;

create table `system_property` (
  `ID` BIGINT not null auto_increment comment 'ID'
  , `CODE` VARCHAR(256) comment 'コード'
  , `VALUE` VARCHAR(256) comment '値'
  , `REMARKS` VARCHAR(1000) comment '備考'
  , `CREATED_BY` VARCHAR(256) not null comment '作成者'
  , `CREATED_DATETIME` DATETIME not null comment '作成日時'
  , `UPDATED_BY` VARCHAR(256) comment '更新者'
  , `UPDATED_DATETIME` DATETIME comment '更新日時'
  , constraint `system_property_PKC` primary key (`ID`)
) comment 'システムプロパティ' ;

alter table `system_property` add unique `system_property_IX1` (`CODE`) ;

-- 権限
drop table if exists `role_authority` cascade;

create table `role_authority` (
  `ID` BIGINT not null auto_increment comment 'ID'
  , `CODE` VARCHAR(256) comment 'コード'
  , `SORT` BIGINT comment 'ソート'
  , `OVERVIEW` VARCHAR(1000) comment '概要'
  , `DESCRIPTION` VARCHAR(1000) comment '説明'
  , `ENABLE_FLAG` INT not null comment '有効フラグ'
  , `CREATED_BY` VARCHAR(256) not null comment '作成者'
  , `CREATED_DATETIME` DATETIME not null comment '作成日時'
  , `UPDATED_BY` VARCHAR(256) comment '更新者'
  , `UPDATED_DATETIME` DATETIME comment '更新日時'
  , constraint `role_authority_PKC` primary key (`ID`)
) comment '権限' ;

alter table `role_authority` add unique `role_authority_IX1` (`CODE`) ;

-- ユーザー権限
drop table if exists `user_authority` cascade;

create table `user_authority` (
  `ID` BIGINT not null auto_increment comment 'ID'
  , `ROLE_ID` BIGINT not null comment 'ロールID'
  , `CODE` VARCHAR(256) comment 'コード'
  , `ENABLE_FLAG` INT not null comment '有効フラグ'
  , `CREATED_BY` VARCHAR(256) not null comment '作成者'
  , `CREATED_DATETIME` DATETIME not null comment '作成日時'
  , `UPDATED_BY` VARCHAR(256) comment '更新者'
  , `UPDATED_DATETIME` DATETIME comment '更新日時'
  , constraint `user_authority_PKC` primary key (`ID`)
) comment 'ユーザー権限' ;

alter table `user_authority` add unique `user_authority_IX1` (`ROLE_ID`,`CODE`) ;

-- 加盟店-店舗情報
drop table if exists `merchant_shop` cascade;

create table `merchant_shop` (
  `ID` BIGINT not null auto_increment comment 'ID'
  , `MERCHANT_COMPANY_ID` BIGINT not null comment '企業ID'
  , `EC_SHOP_CODE` VARCHAR(100) comment 'EC店舗コード'
  , `SHOP_NAME` VARCHAR(200) comment '店舗名'
  , `SHOP_PHONE_NUMBER` VARCHAR(30) comment '店舗電話番号'
  , `SHOP_ADDRESS` VARCHAR(500) comment '店舗住所'
  , `SHOP_MANAGER_NAME` VARCHAR(200) comment '店舗責任者氏名'
  , `MERCHANT_PAYMENT_TYPE_ID` BIGINT comment '決済種別ID'
  , `ANY_MESSAGE` VARCHAR(100) comment '任意メッセージ'
  , `SALT_KEY` VARCHAR(4) comment '店舗鍵'
  , `RUNNING_STATUS` VARCHAR(256) not null comment '稼働状態'
  , `ENABLE_FLAG` INT not null comment '有効フラグ'
  , `CREATED_BY` VARCHAR(256) not null comment '作成者'
  , `CREATED_DATETIME` DATETIME not null comment '作成日時'
  , `UPDATED_BY` VARCHAR(256) comment '更新者'
  , `UPDATED_DATETIME` DATETIME comment '更新日時'
  , constraint `merchant_shop_PKC` primary key (`ID`)
) comment '加盟店-店舗情報' ;

-- 加盟店-企業情報
drop table if exists `merchant_company` cascade;

create table `merchant_company` (
  `ID` BIGINT not null auto_increment comment 'ID'
  , `MID` VARCHAR(30) not null comment 'MID'
  , `MALLMAP_ID` VARCHAR(256) comment 'MallMAP ID'
  , `CCID` VARCHAR(256) comment 'CCID'
  , `CERTIFICATION_KEY` VARCHAR(256) comment '認証鍵'
  , `COMPANY_NAME` VARCHAR(200) not null comment '企業名'
  , `MAIN_PHONE_NUMBER` VARCHAR(30) comment '代表電話番号'
  , `MAIN_ADDRESS` VARCHAR(500) comment '代表住所'
  , `REPRESENTATIVE_NAME` VARCHAR(200) comment '代表者氏名'
  , `EMAIL` VARCHAR(256) comment 'メールアドレス'
  , `RUNNING_STATUS` VARCHAR(256) not null comment '稼働状態'
  , `ENABLE_FLAG` INT not null comment '有効フラグ'
  , `CREATED_BY` VARCHAR(256) not null comment '作成者'
  , `CREATED_DATETIME` DATETIME not null comment '作成日時'
  , `UPDATED_BY` VARCHAR(256) comment '更新者'
  , `UPDATED_DATETIME` DATETIME comment '更新日時'
  , constraint `merchant_company_PKC` primary key (`ID`)
) comment '加盟店-企業情報' ;

alter table `merchant_company` add unique `merchant_company_IX1` (`MID`) ;

-- 決済種別
drop table if exists `payment_type` cascade;

create table `payment_type` (
  `ID` BIGINT not null auto_increment comment 'ID'
  , `NAME` VARCHAR(200) not null comment '名前'
  , `LOGO` VARCHAR(2000) comment 'ロゴ'
  , `ENABLE_FLAG` INT not null comment '有効フラグ'
  , `CREATED_BY` VARCHAR(256) not null comment '作成者'
  , `CREATED_DATETIME` DATETIME not null comment '作成日時'
  , `UPDATED_BY` VARCHAR(256) comment '更新者'
  , `UPDATED_DATETIME` DATETIME comment '更新日時'
  , constraint `payment_type_PKC` primary key (`ID`)
) comment '決済種別' ;

-- システムログ
drop table if exists `system_log` cascade;

create table `system_log` (
  `ID` BIGINT not null auto_increment comment 'ID'
  , `CODE` VARCHAR(256) not null comment 'コード'
  , `CREATED_BY` VARCHAR(256) not null comment '作成者'
  , `CREATED_DATETIME` DATETIME not null comment '作成日時'
  , `UPDATED_BY` VARCHAR(256) comment '更新者'
  , `UPDATED_DATETIME` DATETIME comment '更新日時'
  , constraint `system_log_PKC` primary key (`ID`)
) comment 'システムログ' ;

-- 決済ログ
drop table if exists `payment_log` cascade;

create table `payment_log` (
  `ID` BIGINT not null auto_increment comment 'ID'
  , `CODE` VARCHAR(256) not null comment 'コード'
  , `MID` VARCHAR(30) not null comment 'MID'
  , `MERCHANT_SHOP_ID` BIGINT not null comment '店舗ID'
  , `TERMINAL_ID` VARCHAR(30) not null comment 'ターミナルID'
  , `SLIP_NO` VARCHAR(30) not null comment '伝票No.'
  , `PAYMENT_TYPE_ID` BIGINT not null comment '決済種別ID'
  , `CREATED_BY` VARCHAR(256) not null comment '作成者'
  , `CREATED_DATETIME` DATETIME not null comment '作成日時'
  , `UPDATED_BY` VARCHAR(256) comment '更新者'
  , `UPDATED_DATETIME` DATETIME comment '更新日時'
  , constraint `payment_log_PKC` primary key (`ID`)
) comment '決済ログ' ;