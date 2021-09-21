USE `cryptobank` ;
-- inserts voor BankAccount tests
INSERT INTO cryptobank.bankaccount ("IBAN", "balance") VALUES ('NL24COKI3309054260',5000000); -- LET OP: dit is het account van de bank
INSERT INTO cryptobank.bankaccount ("IBAN", "balance") VALUES ('NL69COKI5000000003',1000000),('NL13COKI8974196092',1000),('NL87COKI9583557878',1000),('NL49COKI7517641892',0);

-- inserts voor Order tests
INSERT INTO cryptobank.`order` (orderId, abbreviation, assetAmount, desiredPrice, iban, datetimecreated, orderType) VALUES (1, 'DOGE', 10, 0.21, 'NL69COKI5000000003', '2021-09-02 17:07:08', 1);
INSERT INTO cryptobank.`order` (orderId, abbreviation, assetAmount, desiredPrice, iban, datetimecreated, orderType) VALUES (2, 'CAKE', 10, 24.44, 'NL69COKI5000000003', '2021-09-02 17:05:16', 1);
INSERT INTO cryptobank.`order` (orderId, abbreviation, assetAmount, desiredPrice, iban, datetimecreated, orderType) VALUES (3, 'ETH', 1.6, 3130, 'NL69COKI5000000003', '2021-09-08 19:05:22', 1);
INSERT INTO cryptobank.`order` (orderId, abbreviation, assetAmount, desiredPrice, iban, datetimecreated, orderType) VALUES (4, 'ETH', 16, 3130, 'NL13COKI8974196092', '2021-09-09 21:11:12', 1);

-- inserts voor Transaction tests
INSERT INTO cryptobank.transaction (transactionId, abbreviation, assetAmount, assetPrice, ibanBuyer, ibanSeller, transactioncost, datetimeprocessed) VALUES (1, 'BTC', 2, 46123.25, 'NL69COKI5000000003', 'NL24COKI3309054260', 5.25, '2021-09-01 10:47:08');
INSERT INTO cryptobank.transaction (transactionId, abbreviation, assetAmount, assetPrice, ibanBuyer, ibanSeller, transactioncost, datetimeprocessed) VALUES (2, 'CAKE', 25, 19.75, 'NL69COKI5000000003', 'NL24COKI3309054260', 8.3, '2021-08-31 09:15:36');
INSERT INTO cryptobank.transaction (transactionId, abbreviation, assetAmount, assetPrice, ibanBuyer, ibanSeller, transactioncost, datetimeprocessed) VALUES (3, 'DOGE', 12500, 0.20, 'NL69COKI5000000003', 'NL24COKI3309054260', 9.95, '2021-09-02 16:47:51');
