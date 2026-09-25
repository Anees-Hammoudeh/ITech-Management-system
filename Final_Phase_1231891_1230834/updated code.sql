DROP DATABASE IF EXISTS itech_db;
CREATE DATABASE itech_db;
USE itech_db;
CREATE TABLE Category (
    Category_ID INT AUTO_INCREMENT PRIMARY KEY,
    Category_Name VARCHAR(100) NOT NULL UNIQUE,
    Unit_Size INT NOT NULL DEFAULT 1 CHECK (Unit_Size > 0)
);
CREATE TABLE Brand (
    Brand_ID INT AUTO_INCREMENT PRIMARY KEY,
    Brand_Name VARCHAR(100) NOT NULL UNIQUE,
    Website VARCHAR(200)
);
CREATE TABLE Supplier (
    Supplier_ID INT AUTO_INCREMENT PRIMARY KEY,
    Contact_Name VARCHAR(100) NOT NULL,
    Company_Name VARCHAR(100) NOT NULL,
    Address VARCHAR(200)
);
CREATE TABLE Customer (
    Customer_ID INT AUTO_INCREMENT PRIMARY KEY,
    First_Name VARCHAR(50) NOT NULL,
    Last_Name VARCHAR(50) NOT NULL
);
CREATE TABLE Employee (
    Employee_ID INT AUTO_INCREMENT PRIMARY KEY,
    First_Name VARCHAR(50) NOT NULL,
    Last_Name VARCHAR(50) NOT NULL,
    Position VARCHAR(50) NOT NULL,
    Salary DECIMAL(10,2) NOT NULL CHECK (Salary >=0),
    Supervisor_ID INT,
    FOREIGN KEY (Supervisor_ID)
    REFERENCES Employee(Employee_ID)
    ON DELETE SET NULL
    ON UPDATE CASCADE
);
CREATE TABLE Storage_Section (
    Section_ID INT AUTO_INCREMENT PRIMARY KEY,
    Section_Name VARCHAR(100) NOT NULL,
    Section_Type VARCHAR(50) NOT NULL,
    Capacity INT NOT NULL CHECK (Capacity > 0)
);
CREATE TABLE Product (
    Product_ID INT AUTO_INCREMENT PRIMARY KEY,
    Product_Name VARCHAR(100) NOT NULL,
    Model VARCHAR(100),
    Price DECIMAL(10,2) NOT NULL CHECK (Price >= 0),
    Category_ID INT NOT NULL,
    Brand_ID INT NOT NULL,
    FOREIGN KEY (Category_ID)
    REFERENCES Category(Category_ID)
    ON DELETE NO ACTION
    ON UPDATE CASCADE,
    FOREIGN KEY (Brand_ID)
    REFERENCES Brand(Brand_ID)
    ON DELETE NO ACTION
    ON UPDATE CASCADE
);
CREATE TABLE Supplier_Phone (
    Supplier_ID INT NOT NULL,
    Phone VARCHAR(20) NOT NULL,
    PRIMARY KEY (Supplier_ID, Phone),
    FOREIGN KEY (Supplier_ID)
    REFERENCES Supplier(Supplier_ID)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);
CREATE TABLE Customer_Phone (
    Customer_ID INT NOT NULL,
    Phone VARCHAR(20) NOT NULL,
    PRIMARY KEY (Customer_ID, Phone),
    FOREIGN KEY (Customer_ID)
    REFERENCES Customer(Customer_ID)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);
CREATE TABLE Employee_Phone (
    Employee_ID INT NOT NULL,
    Phone VARCHAR(20) NOT NULL,
    PRIMARY KEY (Employee_ID, Phone),
    FOREIGN KEY (Employee_ID)
    REFERENCES Employee(Employee_ID)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);
CREATE TABLE Purchase (
    Purchase_ID INT AUTO_INCREMENT PRIMARY KEY,
    Employee_ID INT NOT NULL,
    Supplier_ID INT NOT NULL,
    Purchase_Date DATE NOT NULL,
    Delivery_Date DATE,
	Purchase_Status VARCHAR(20) DEFAULT 'Pending',
    FOREIGN KEY (Employee_ID)
    REFERENCES Employee(Employee_ID)
    ON DELETE NO ACTION
    ON UPDATE CASCADE,
    FOREIGN KEY (Supplier_ID)
    REFERENCES Supplier(Supplier_ID)
    ON UPDATE CASCADE
);
CREATE TABLE Sale (
    Sale_ID INT AUTO_INCREMENT PRIMARY KEY,
    Employee_ID INT NOT NULL,
    Customer_ID INT NOT NULL,
    Sale_Date DATE NOT NULL,
    FOREIGN KEY (Employee_ID)
    REFERENCES Employee(Employee_ID)
    ON DELETE NO ACTION
    ON UPDATE CASCADE,
    FOREIGN KEY (Customer_ID)
    REFERENCES Customer(Customer_ID)
    ON UPDATE CASCADE
);
CREATE TABLE Supplier_Product (
    Supplier_ID INT NOT NULL,
    Product_ID INT NOT NULL,
    Supply_Price DECIMAL(10,2) NOT NULL,
    PRIMARY KEY (Supplier_ID, Product_ID),
    FOREIGN KEY (Supplier_ID)
    REFERENCES Supplier(Supplier_ID)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
    FOREIGN KEY (Product_ID)
    REFERENCES Product(Product_ID)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);
CREATE TABLE Purchase_Details (
    Purchase_ID INT NOT NULL,
    Product_ID INT NOT NULL,
    Unit_Price DECIMAL(10,2) NOT NULL,
    Quantity INT NOT NULL CHECK (Quantity >= 0),
    PRIMARY KEY (Purchase_ID, Product_ID),
    FOREIGN KEY (Purchase_ID)
    REFERENCES Purchase(Purchase_ID)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
    FOREIGN KEY (Product_ID)
    REFERENCES Product(Product_ID)
    ON DELETE NO ACTION
    ON UPDATE CASCADE
);
CREATE TABLE Sale_Details (
    Sale_ID INT NOT NULL,
    Product_ID INT NOT NULL,
    Unit_Price DECIMAL(10,2) NOT NULL CHECK (Unit_Price >= 0),
    Quantity INT NOT NULL CHECK (Quantity > 0),
    Discount DECIMAL(10,2) CHECK (Discount >= 0),
    PRIMARY KEY (Sale_ID, Product_ID),
    FOREIGN KEY (Sale_ID)
    REFERENCES Sale(Sale_ID)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
    FOREIGN KEY (Product_ID)
    REFERENCES Product(Product_ID)
    ON UPDATE CASCADE
);
CREATE TABLE Storage_Details (
    Product_ID INT NOT NULL,
    Section_ID INT NOT NULL,
    Quantity INT NOT NULL CHECK (Quantity >= 0),
    PRIMARY KEY (Product_ID, Section_ID),
    FOREIGN KEY (Product_ID)
    REFERENCES Product(Product_ID)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
    FOREIGN KEY (Section_ID)
    REFERENCES Storage_Section(Section_ID)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);
CREATE TABLE Warranty (
    Sale_ID INT NOT NULL,
    Product_ID INT NOT NULL,
    Warranty_Number VARCHAR(50) NOT NULL,
    Warranty_Type VARCHAR(100) NOT NULL,
    Warranty_Period VARCHAR(50) NOT NULL,
    Start_Date DATE NOT NULL,
    PRIMARY KEY (Sale_ID, Product_ID, Warranty_Number),
    FOREIGN KEY (Sale_ID, Product_ID)
    REFERENCES Sale_Details(Sale_ID, Product_ID)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);
CREATE TABLE Payment (
    Payment_ID INT AUTO_INCREMENT PRIMARY KEY,
    Sale_ID INT NOT NULL,
    Amount DECIMAL(10,2) NOT NULL CHECK (Amount >=0),
    Payment_Method VARCHAR(50) NOT NULL,
    Payment_Status VARCHAR(50) NOT NULL,
    Payment_Date DATE NOT NULL,
    FOREIGN KEY (Sale_ID)
    REFERENCES Sale(Sale_ID)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);
CREATE TABLE Return_Record (
    Return_ID INT AUTO_INCREMENT PRIMARY KEY,
    Sale_ID INT NOT NULL,
    Product_ID INT NOT NULL,
    Return_Date DATE NOT NULL,
    Quantity INT NOT NULL,
    Return_Reason VARCHAR(200),
    Return_Status VARCHAR(50) NOT NULL,
    FOREIGN KEY (Sale_ID, Product_ID)
    REFERENCES Sale_Details(Sale_ID, Product_ID)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);
CREATE TABLE Stock_Movement_History (
    Movement_ID INT AUTO_INCREMENT PRIMARY KEY,
    Product_ID INT NOT NULL,
    Source_Section_ID INT,
    Destination_Section_ID INT,
    Movement_Type VARCHAR(50) NOT NULL,
    Quantity INT NOT NULL CHECK (Quantity > 0),
    Movement_Date DATE NOT NULL,
    FOREIGN KEY (Product_ID)
    REFERENCES Product(Product_ID)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
    FOREIGN KEY (Source_Section_ID)
    REFERENCES Storage_Section(Section_ID)
    ON DELETE SET NULL
    ON UPDATE CASCADE,
    FOREIGN KEY (Destination_Section_ID)
    REFERENCES Storage_Section(Section_ID)
    ON DELETE SET NULL
    ON UPDATE CASCADE
);
INSERT INTO Category (Category_Name, Unit_Size) VALUES
('Smartphone', 2), ('Laptop', 5), ('Tablet', 3),
('Accessory', 1), ('Audio', 1), ('Gaming', 5);
INSERT INTO Brand (Brand_Name, Website) VALUES
('Apple', 'https://www.apple.com'),
('Samsung', 'https://www.samsung.com'),
('Dell', 'https://www.dell.com'),
('HP', 'https://www.hp.com'),
('Lenovo', 'https://www.lenovo.com'),
('Sony', 'https://www.sony.com'),
('Logitech', 'https://www.logitech.com'),
('Asus', 'https://www.asus.com');
INSERT INTO Supplier (Contact_Name, Company_Name, Address) VALUES
('Ahmad Saleh',  'TechSource Palestine', 'Ramallah - Al Ersal'),
('Mona Khalil',  'Smart Import Co.',     'Al-Bireh'),
('Yousef Nasser','Digital World Supplies','Nablus'),
('Rana Odeh',    'Future Electronics',   'Hebron');
INSERT INTO Supplier_Phone VALUES
(1,'0599001122'),(1,'022981111'),
(2,'0599112233'),(2,'022982222'),
(3,'0599223344'),(4,'0599334455');
INSERT INTO Customer (First_Name, Last_Name) VALUES ('Walk-in','Customer');
INSERT INTO Customer_Phone VALUES (1,'0000000000');
INSERT INTO Customer (First_Name, Last_Name) VALUES ('Omar','Khaled');
INSERT INTO Customer_Phone VALUES (2,'0597002222');
INSERT INTO Customer (First_Name, Last_Name) VALUES ('Lina','Ahmad');
INSERT INTO Customer_Phone VALUES (3,'0597003333');
INSERT INTO Customer (First_Name, Last_Name) VALUES ('Samer','Nassar');
INSERT INTO Customer_Phone VALUES (4,'0597004444');
INSERT INTO Customer (First_Name, Last_Name) VALUES ('Hiba','Saleh');
INSERT INTO Customer_Phone VALUES (5,'0597005555');
INSERT INTO Customer (First_Name, Last_Name) VALUES ('Tariq','Mansour');
INSERT INTO Customer_Phone VALUES (6,'0597006666');
INSERT INTO Customer (First_Name, Last_Name) VALUES ('Noor','Zaid');
INSERT INTO Customer_Phone VALUES (7,'0597007777');
INSERT INTO Customer (First_Name, Last_Name) VALUES ('Yara','Hammad');
INSERT INTO Customer_Phone VALUES (8,'0597008888');
INSERT INTO Customer (First_Name, Last_Name) VALUES ('Adam','Qasem');
INSERT INTO Customer_Phone VALUES (9,'0597009999');
INSERT INTO Customer (First_Name, Last_Name) VALUES ('Khaled','Mansour');
INSERT INTO Customer_Phone VALUES (10,'0597010000');
INSERT INTO Customer (First_Name, Last_Name) VALUES ('Rania','Haddad');
INSERT INTO Customer_Phone VALUES (11,'0597011111');
INSERT INTO Customer (First_Name, Last_Name) VALUES ('Faris','Barakat');
INSERT INTO Customer_Phone VALUES (12,'0597012222');
INSERT INTO Customer (First_Name, Last_Name) VALUES ('Dina','Nabulsi');
INSERT INTO Customer_Phone VALUES (13,'0597013333');
INSERT INTO Customer (First_Name, Last_Name) VALUES ('Ziad','Awad');
INSERT INTO Customer_Phone VALUES (14,'0597014444');
INSERT INTO Customer (First_Name, Last_Name) VALUES ('Samar','Khalil');
INSERT INTO Customer_Phone VALUES (15,'0597015555');
INSERT INTO Customer (First_Name, Last_Name) VALUES ('Bilal','Issa');
INSERT INTO Customer_Phone VALUES (16,'0597016666');
INSERT INTO Customer (First_Name, Last_Name) VALUES ('Nadia','Freij');
INSERT INTO Customer_Phone VALUES (17,'0597017777');
INSERT INTO Customer (First_Name, Last_Name) VALUES ('Tamer','Salah');
INSERT INTO Customer_Phone VALUES (18,'0597018888');
INSERT INTO Customer (First_Name, Last_Name) VALUES ('Hala','Qasim');
INSERT INTO Customer_Phone VALUES (19,'0597019999');
INSERT INTO Customer (First_Name, Last_Name) VALUES ('Jawad','Jaber');
INSERT INTO Customer_Phone VALUES (20,'0597020000');
INSERT INTO Employee(First_Name,Last_Name,Position,Salary,Supervisor_ID) VALUES
('Ahmad','Saleh','Manager',2500,null);
INSERT INTO Employee(First_Name,Last_Name,Position,Salary,Supervisor_ID) VALUES
('Mona','Ali','Manager',2500,null);
INSERT INTO Employee(First_Name,Last_Name,Position,Salary,Supervisor_ID) VALUES
('Rami','Hassan','Accountant',1400,1),
('Lina','Omar','Accountant',1200,1),
('Sami','Naser','Sales Employee',1300,1),
('Huda','Khaled','Sales Employee',1100,1),
('Omar','Taha','Warehouse Employee',1200,1),
('Noor','Yousef','Warehouse Employee',1000,1),
('Kareem','Said','Cashier',1100,1),
('Sara','Mansour','Cashier',900,1);
INSERT INTO Employee_Phone VALUES
(1,'0598001111'),(2,'0598002222'),
(2,'0598003333'),(1,'022981234');
INSERT INTO Storage_Section (Section_Name, Section_Type, Capacity) VALUES
('Front Showroom A',   'Showroom',     100),
('Front Showroom B',   'Showroom',     100),
('Backroom Storage 1', 'Backroom',     200),
('Backroom Storage 2', 'Backroom',     200),
('Accessories Shelf',  'Showroom',     300),
('Repair Waiting Shelf','Service Area', 50);
INSERT INTO Product (Product_Name, Model, Price, Category_ID, Brand_ID) VALUES
('iPhone 15',              'A3090',     950.00, 1, 1),
('Samsung Galaxy S24',     'SM-S921',   820.00, 1, 2),
('Samsung Galaxy A55',     'SM-A556',   380.00, 1, 2),
('Dell Inspiron 15',       '3520',      650.00, 2, 3),
('HP Pavilion 14',         '14-dv2000', 720.00, 2, 4),
('Lenovo IdeaPad 3',       '15IAU7',    580.00, 2, 5),
('iPad 10th Gen',          'A2696',     470.00, 3, 1),
('Logitech Wireless Mouse','M185',       18.00, 4, 7),
('Logitech Keyboard',      'K380',       42.00, 4, 7),
('Sony WH-CH720N',         'WH-CH720N', 120.00, 5, 6),
('Samsung Galaxy Buds FE', 'SM-R400',    75.00, 5, 2),
('Asus ROG Gaming Laptop', 'G16',      1450.00, 6, 8),
('Apple USB-C Charger',    '20W',        25.00, 4, 1),
('Samsung Fast Charger',   '25W',        20.00, 4, 2),
('Logitech Webcam',        'C920',       85.00, 4, 7),
('Dell Gaming Mouse',      'GM500',      35.00, 4, 3),
('HP USB Flash Drive',     '64GB',       12.00, 4, 4),
('Lenovo Tab M10',         'TB328FU',   210.00, 3, 5);
INSERT INTO Supplier_Product VALUES
(1,1,850),(1,7,400),(1,13,18),
(2,2,730),(2,3,310),(2,11,55),(2,14,14),
(3,4,560),(3,5,620),(3,6,500),(3,12,1280),(3,16,25),(3,17,7),
(4,8,10),(4,9,30),(4,10,90),(4,15,62),(4,18,160);
INSERT INTO Purchase (Employee_ID, Supplier_ID, Purchase_Date, Delivery_Date, Purchase_Status) VALUES
(2, 1, '2026-04-01','2026-04-03','Received'),
(2, 2, '2026-04-05','2026-04-07','Received'),
(5, 3, '2026-04-10','2026-04-12','Received'),
(2, 4, '2026-04-15','2026-04-16','Received'),
(5, 2, '2026-04-20','2026-04-22','Received'),
(2, 3, '2026-04-25','2026-04-27','Received'),
(2, 1, '2026-05-01','2026-05-03','Received'),
(5, 2, '2026-05-05','2026-05-07','Received'),
(2, 3, '2026-05-10','2026-05-12','Received'),
(5, 4, '2026-05-15','2026-05-17','Received'),
(2, 1, '2026-06-01','2026-06-03','Received'),
(5, 3, '2026-06-10', NULL,       'Pending');
INSERT INTO Purchase_Details VALUES
(1,1,850,8),(1,7,400,6),(1,13,18,30),
(2,2,730,7),(2,3,310,10),(2,11,55,20),(2,14,14,25),
(3,4,560,6),(3,5,620,5),(3,6,500,7),(3,12,1280,3),
(4,8,10,40),(4,9,30,25),(4,10,90,12),(4,15,62,10),
(5,2,730,4),(5,11,55,15),
(6,16,25,20),(6,17,7,50),(6,18,160,8),
(7,1,850,5),(7,2,730,5),(7,13,18,50),
(8,8,10,60),(8,9,30,40),(8,14,14,30),(8,11,55,20),
(9,4,560,4),(9,5,620,4),(9,6,500,5),
(10,10,90,10),(10,15,62,8),(10,16,25,15),
(11,1,850,6),(11,7,400,4),(11,3,310,8),
(12,12,1280,2),(12,18,160,5);
INSERT INTO Storage_Details VALUES
(1,1,8),(1,3,3),
(2,1,4),(2,3,10),
(3,1,5),(3,3,13),
(4,2,5),(4,4,8),
(5,2,5),(5,4,7),
(6,2,3),(6,4,9),
(7,1,5),(7,3,4),
(8,5,89),(9,5,64),
(10,2,5),(10,4,17),
(11,5,34),(12,2,1),(12,4,2),
(13,5,74),(14,5,51),
(15,5,18),(16,5,33),(17,5,45),
(18,1,3),(18,3,5);
INSERT INTO Sale (Employee_ID, Customer_ID, Sale_Date) VALUES
(2,1,'2026-04-18'),(2,3,'2026-04-18'),
(4,1,'2026-04-19'),(2,5,'2026-04-20'),
(4,6,'2026-04-21'),(2,1,'2026-04-22'),
(4,2,'2026-04-23'),(2,8,'2026-04-24'),
(4,9,'2026-04-25'),(2,4,'2026-04-26'),
(4,10,'2026-05-01'),(2,11,'2026-05-03'),
(4,12,'2026-05-05'),(2,1, '2026-05-08'),
(4,13,'2026-05-10'),(2,14,'2026-05-12'),
(4,15,'2026-05-15'),(2,16,'2026-05-18'),
(4,2, '2026-06-01'),(2,17,'2026-06-05'),
(4,18,'2026-06-10'),(2,1, '2026-06-18');
INSERT INTO Sale_Details VALUES
(1,1,950,1,NULL),(1,13,25,1,0),
(2,2,820,1,20),(2,11,75,1,NULL),
(3,4,650,1,0),(3,8,18,2,NULL),
(4,10,120,1,10),(4,9,42,1,NULL),
(5,12,1450,1,50),(5,15,85,1,NULL),
(6,3,380,1,NULL),(6,14,20,2,0),
(7,7,470,1,NULL),(7,13,25,2,NULL),
(8,18,210,1,5),(8,17,12,2,NULL),
(9,5,720,1,25),(9,16,35,1,NULL),
(10,11,75,2,NULL),(10,8,18,1,0),
(11,2,820,2,20),(11,9,42,1,0),
(12,4,650,1,0),(12,5,720,1,0),
(13,7,470,1,0),(13,8,18,3,0),
(14,8,18,5,0),(14,9,42,2,5),(14,17,12,3,0),
(15,12,1450,1,100),
(16,10,120,1,0),(16,11,75,2,10),
(17,6,580,1,0),(17,13,25,2,0),
(18,13,25,3,0),(18,14,20,2,0),(18,17,12,4,0),
(19,1,950,1,0),(19,13,25,1,0),
(20,3,380,1,0),(20,14,20,1,0),
(21,15,85,1,0),(21,9,42,1,0),
(22,4,650,1,0),(22,5,720,1,0);
UPDATE Storage_Details SET Quantity=Quantity-1 WHERE Product_ID=1  AND Section_ID=1;
UPDATE Storage_Details SET Quantity=Quantity-1 WHERE Product_ID=13 AND Section_ID=5;
UPDATE Storage_Details SET Quantity=Quantity-1 WHERE Product_ID=2  AND Section_ID=1;
UPDATE Storage_Details SET Quantity=Quantity-1 WHERE Product_ID=11 AND Section_ID=5;
UPDATE Storage_Details SET Quantity=Quantity-1 WHERE Product_ID=4  AND Section_ID=2;
UPDATE Storage_Details SET Quantity=Quantity-2 WHERE Product_ID=8  AND Section_ID=5;
UPDATE Storage_Details SET Quantity=Quantity-1 WHERE Product_ID=10 AND Section_ID=2;
UPDATE Storage_Details SET Quantity=Quantity-1 WHERE Product_ID=9  AND Section_ID=5;
UPDATE Storage_Details SET Quantity=Quantity-1 WHERE Product_ID=12 AND Section_ID=2;
UPDATE Storage_Details SET Quantity=Quantity-1 WHERE Product_ID=15 AND Section_ID=5;
UPDATE Storage_Details SET Quantity=Quantity-1 WHERE Product_ID=3  AND Section_ID=1;
UPDATE Storage_Details SET Quantity=Quantity-2 WHERE Product_ID=14 AND Section_ID=5;
UPDATE Storage_Details SET Quantity=Quantity-1 WHERE Product_ID=7  AND Section_ID=1;
UPDATE Storage_Details SET Quantity=Quantity-2 WHERE Product_ID=13 AND Section_ID=5;
UPDATE Storage_Details SET Quantity=Quantity-1 WHERE Product_ID=18 AND Section_ID=1;
UPDATE Storage_Details SET Quantity=Quantity-2 WHERE Product_ID=17 AND Section_ID=5;
UPDATE Storage_Details SET Quantity=Quantity-1 WHERE Product_ID=5  AND Section_ID=2;
UPDATE Storage_Details SET Quantity=Quantity-1 WHERE Product_ID=16 AND Section_ID=5;
UPDATE Storage_Details SET Quantity=Quantity-2 WHERE Product_ID=11 AND Section_ID=5;
UPDATE Storage_Details SET Quantity=Quantity-1 WHERE Product_ID=8  AND Section_ID=5;
UPDATE Storage_Details SET Quantity=Quantity-2 WHERE Product_ID=2  AND Section_ID=3;
UPDATE Storage_Details SET Quantity=Quantity-1 WHERE Product_ID=9  AND Section_ID=5;
UPDATE Storage_Details SET Quantity=Quantity-1 WHERE Product_ID=4  AND Section_ID=4;
UPDATE Storage_Details SET Quantity=Quantity-1 WHERE Product_ID=5  AND Section_ID=4;
UPDATE Storage_Details SET Quantity=Quantity-1 WHERE Product_ID=7  AND Section_ID=3;
UPDATE Storage_Details SET Quantity=Quantity-3 WHERE Product_ID=8  AND Section_ID=5;
UPDATE Storage_Details SET Quantity=Quantity-5 WHERE Product_ID=8  AND Section_ID=5;
UPDATE Storage_Details SET Quantity=Quantity-2 WHERE Product_ID=9  AND Section_ID=5;
UPDATE Storage_Details SET Quantity=Quantity-3 WHERE Product_ID=17 AND Section_ID=5;
UPDATE Storage_Details SET Quantity=Quantity-1 WHERE Product_ID=12 AND Section_ID=4;
UPDATE Storage_Details SET Quantity=Quantity-1 WHERE Product_ID=10 AND Section_ID=4;
UPDATE Storage_Details SET Quantity=Quantity-2 WHERE Product_ID=11 AND Section_ID=5;
UPDATE Storage_Details SET Quantity=Quantity-1 WHERE Product_ID=6  AND Section_ID=4;
UPDATE Storage_Details SET Quantity=Quantity-2 WHERE Product_ID=13 AND Section_ID=5;
UPDATE Storage_Details SET Quantity=Quantity-3 WHERE Product_ID=13 AND Section_ID=5;
UPDATE Storage_Details SET Quantity=Quantity-2 WHERE Product_ID=14 AND Section_ID=5;
UPDATE Storage_Details SET Quantity=Quantity-4 WHERE Product_ID=17 AND Section_ID=5;
UPDATE Storage_Details SET Quantity=Quantity-1 WHERE Product_ID=1  AND Section_ID=1;
UPDATE Storage_Details SET Quantity=Quantity-1 WHERE Product_ID=13 AND Section_ID=5;
UPDATE Storage_Details SET Quantity=Quantity-1 WHERE Product_ID=3  AND Section_ID=3;
UPDATE Storage_Details SET Quantity=Quantity-1 WHERE Product_ID=14 AND Section_ID=5;
UPDATE Storage_Details SET Quantity=Quantity-1 WHERE Product_ID=15 AND Section_ID=5;
UPDATE Storage_Details SET Quantity=Quantity-1 WHERE Product_ID=9  AND Section_ID=5;
UPDATE Storage_Details SET Quantity=Quantity-1 WHERE Product_ID=4  AND Section_ID=4;
UPDATE Storage_Details SET Quantity=Quantity-1 WHERE Product_ID=5  AND Section_ID=4;
INSERT INTO Payment (Sale_ID, Amount, Payment_Method, Payment_Status, Payment_Date) VALUES
(1, 975,  'Cash',        'Paid',    '2026-04-18'),
(2, 500,  'Installment', 'Partial', '2026-04-18'),
(2, 375,  'Installment', 'Paid',    '2026-05-18'),
(3, 686,  'Credit Card', 'Paid',    '2026-04-19'),
(4, 152,  'Cash',        'Paid',    '2026-04-20'),
(5, 800,  'Installment', 'Partial', '2026-04-21'),
(5, 685,  'Installment', 'Paid',    '2026-05-21'),
(6, 420,  'Cash',        'Paid',    '2026-04-22'),
(7, 520,  'Credit Card', 'Paid',    '2026-04-23'),
(8, 229,  'Cash',        'Paid',    '2026-04-24'),
(9, 400,  'Installment', 'Partial', '2026-04-25'),
(9, 330,  'Installment', 'Paid',    '2026-05-25'),
(10,93,   'Cash',        'Paid',    '2026-04-26'),
(11,1684, 'Cash',        'Paid',    '2026-05-01'),
(12,800,  'Installment', 'Partial', '2026-05-03'),
(13,524,  'Credit Card', 'Paid',    '2026-05-05'),
(14,211,  'Cash',        'Paid',    '2026-05-08'),
(15,700,  'Installment', 'Partial', '2026-05-10'),
(16,260,  'Cash',        'Paid',    '2026-05-12'),
(17,630,  'Credit Card', 'Paid',    '2026-05-15'),
(18,211,  'Cash',        'Paid',    '2026-05-18'),
(19,975,  'Cash',        'Paid',    '2026-06-01'),
(20,400,  'Cash',        'Paid',    '2026-06-05'),
(21,127,  'Cash',        'Paid',    '2026-06-10');
INSERT INTO Warranty VALUES
(2, 2,  'W-S24-001',   'Manufacturer Warranty', '12 Months', '2026-04-18'),
(4, 10, 'W-SONY-001',  'Manufacturer Warranty', '6 Months',  '2026-04-20'),
(5, 12, 'W-ASUS-001',  'Manufacturer Warranty', '24 Months', '2026-04-21'),
(7, 7,  'W-IPAD-001',  'Manufacturer Warranty', '12 Months', '2026-04-23'),
(8, 18, 'W-LENTAB-001','Manufacturer Warranty', '12 Months', '2026-04-24'),
(9, 5,  'W-HP-001',    'Manufacturer Warranty', '24 Months', '2026-04-25'),
(11,2,  'W-S24-002',   'Manufacturer Warranty', '12 Months', '2026-05-01'),
(12,4,  'W-DELL-002',  'Store Warranty',         '12 Months', '2026-05-03'),
(12,5,  'W-HP-002',    'Manufacturer Warranty', '24 Months', '2026-05-03'),
(13,7,  'W-IPAD-002',  'Manufacturer Warranty', '12 Months', '2026-05-05'),
(15,12, 'W-ASUS-002',  'Manufacturer Warranty', '24 Months', '2026-05-10'),
(17,6,  'W-LENI-001',  'Manufacturer Warranty', '12 Months', '2026-05-15'),
(19,1,  'W-IPH15-002', 'Manufacturer Warranty', '12 Months', '2026-06-01');
INSERT INTO Return_Record
(Sale_ID, Product_ID, Return_Date, Quantity, Return_Reason, Return_Status) VALUES
(6, 14, '2026-04-24', 1, 'Wrong charger type',    'Approved'),
(10,11, '2026-04-28', 1, 'Audio issue',            'Approved'),
(11,9,  '2026-05-04', 1, 'Changed mind',           'Approved'),
(13,8,  '2026-05-08', 1, 'Defective item',         'Approved'),
(16,11, '2026-05-15', 1, 'Sound quality issue',    'Approved'),
(14,17, '2026-05-10', 2, 'No valid reason',        'Rejected'),
(20,14, '2026-06-07', 1, 'Opened package',         'Rejected'),
(15,12, '2026-05-14', 1, 'Screen flickering',      'Pending'),
(19,1,  '2026-06-04', 1, 'Battery issue',          'Pending');

UPDATE Storage_Details SET Quantity=Quantity+1 WHERE Product_ID=14 AND Section_ID=5;
UPDATE Storage_Details SET Quantity=Quantity+1 WHERE Product_ID=11 AND Section_ID=5;
UPDATE Storage_Details SET Quantity=Quantity+1 WHERE Product_ID=9  AND Section_ID=5;
UPDATE Storage_Details SET Quantity=Quantity+1 WHERE Product_ID=8  AND Section_ID=5;
UPDATE Storage_Details SET Quantity=Quantity+1 WHERE Product_ID=11 AND Section_ID=5;
INSERT INTO Stock_Movement_History
(Product_ID,Source_Section_ID,Destination_Section_ID,Movement_Type,Quantity,Movement_Date) VALUES
(1,NULL,3,'Purchase',8,'2026-04-03'),(7,NULL,3,'Purchase',6,'2026-04-03'),(13,NULL,5,'Purchase',30,'2026-04-03'),
(2,NULL,3,'Purchase',7,'2026-04-07'),(3,NULL,3,'Purchase',10,'2026-04-07'),(11,NULL,5,'Purchase',20,'2026-04-07'),(14,NULL,5,'Purchase',25,'2026-04-07'),
(4,NULL,4,'Purchase',6,'2026-04-12'),(5,NULL,4,'Purchase',5,'2026-04-12'),(6,NULL,4,'Purchase',7,'2026-04-12'),(12,NULL,4,'Purchase',3,'2026-04-12'),
(8,NULL,5,'Purchase',40,'2026-04-16'),(9,NULL,5,'Purchase',25,'2026-04-16'),(10,NULL,2,'Purchase',12,'2026-04-16'),(15,NULL,5,'Purchase',10,'2026-04-16'),
(2,NULL,3,'Purchase',4,'2026-04-22'),(11,NULL,5,'Purchase',15,'2026-04-22'),
(16,NULL,5,'Purchase',20,'2026-04-27'),(17,NULL,5,'Purchase',50,'2026-04-27'),(18,NULL,1,'Purchase',8,'2026-04-27'),
(1,NULL,3,'Purchase',5,'2026-05-03'),(2,NULL,3,'Purchase',5,'2026-05-03'),(13,NULL,5,'Purchase',50,'2026-05-03'),
(8,NULL,5,'Purchase',60,'2026-05-07'),(9,NULL,5,'Purchase',40,'2026-05-07'),(14,NULL,5,'Purchase',30,'2026-05-07'),(11,NULL,5,'Purchase',20,'2026-05-07'),
(4,NULL,4,'Purchase',4,'2026-05-12'),(5,NULL,4,'Purchase',4,'2026-05-12'),(6,NULL,4,'Purchase',5,'2026-05-12'),
(10,NULL,4,'Purchase',10,'2026-05-17'),(15,NULL,5,'Purchase',8,'2026-05-17'),(16,NULL,5,'Purchase',15,'2026-05-17'),
(1,NULL,1,'Purchase',6,'2026-06-03'),(7,NULL,1,'Purchase',4,'2026-06-03'),(3,NULL,3,'Purchase',8,'2026-06-03'),
(1,1,NULL,'Sale',1,'2026-04-18'),(13,5,NULL,'Sale',1,'2026-04-18'),
(2,1,NULL,'Sale',1,'2026-04-18'),(11,5,NULL,'Sale',1,'2026-04-18'),
(4,2,NULL,'Sale',1,'2026-04-19'),(8,5,NULL,'Sale',2,'2026-04-19'),
(10,2,NULL,'Sale',1,'2026-04-20'),(9,5,NULL,'Sale',1,'2026-04-20'),
(12,2,NULL,'Sale',1,'2026-04-21'),(15,5,NULL,'Sale',1,'2026-04-21'),
(3,1,NULL,'Sale',1,'2026-04-22'),(14,5,NULL,'Sale',2,'2026-04-22'),
(7,1,NULL,'Sale',1,'2026-04-23'),(13,5,NULL,'Sale',2,'2026-04-23'),
(18,1,NULL,'Sale',1,'2026-04-24'),(17,5,NULL,'Sale',2,'2026-04-24'),
(5,2,NULL,'Sale',1,'2026-04-25'),(16,5,NULL,'Sale',1,'2026-04-25'),
(11,5,NULL,'Sale',2,'2026-04-26'),(8,5,NULL,'Sale',1,'2026-04-26'),
(2,3,NULL,'Sale',2,'2026-05-01'),(9,5,NULL,'Sale',1,'2026-05-01'),
(4,4,NULL,'Sale',1,'2026-05-03'),(5,4,NULL,'Sale',1,'2026-05-03'),
(7,3,NULL,'Sale',1,'2026-05-05'),(8,5,NULL,'Sale',3,'2026-05-05'),
(8,5,NULL,'Sale',5,'2026-05-08'),(9,5,NULL,'Sale',2,'2026-05-08'),(17,5,NULL,'Sale',3,'2026-05-08'),
(12,4,NULL,'Sale',1,'2026-05-10'),
(10,4,NULL,'Sale',1,'2026-05-12'),(11,5,NULL,'Sale',2,'2026-05-12'),
(6,4,NULL,'Sale',1,'2026-05-15'),(13,5,NULL,'Sale',2,'2026-05-15'),
(13,5,NULL,'Sale',3,'2026-05-18'),(14,5,NULL,'Sale',2,'2026-05-18'),(17,5,NULL,'Sale',4,'2026-05-18'),
(1,1,NULL,'Sale',1,'2026-06-01'),(13,5,NULL,'Sale',1,'2026-06-01'),
(3,3,NULL,'Sale',1,'2026-06-05'),(14,5,NULL,'Sale',1,'2026-06-05'),
(15,5,NULL,'Sale',1,'2026-06-10'),(9,5,NULL,'Sale',1,'2026-06-10'),
(4,4,NULL,'Sale',1,'2026-06-18'),(5,4,NULL,'Sale',1,'2026-06-18'),
(8,NULL,5,'Return',1,'2026-04-21'),(14,NULL,5,'Return',1,'2026-04-24'),(11,NULL,5,'Return',1,'2026-04-28'),
(9,NULL,5,'Return',1,'2026-05-04'),(8,NULL,5,'Return',1,'2026-05-08'),(11,NULL,5,'Return',1,'2026-05-15'),
(1,3,1,'Transfer',2,'2026-05-20'),
(9,5,2,'Transfer',3,'2026-05-22');
UPDATE Storage_Details SET Quantity=Quantity-2 WHERE Product_ID=1 AND Section_ID=3;
UPDATE Storage_Details SET Quantity=Quantity+2 WHERE Product_ID=1 AND Section_ID=1;
UPDATE Storage_Details SET Quantity=Quantity-3 WHERE Product_ID=9 AND Section_ID=5;
INSERT INTO Storage_Details VALUES (9,2,3);



INSERT INTO Product (Product_ID, Product_Name, Model, Price, Category_ID, Brand_ID)
VALUES (19,'Gaming Keyboard','GK500',120,4,2);
INSERT INTO Product (Product_ID, Product_Name, Model, Price, Category_ID, Brand_ID)
VALUES (20,'Wireless Charger','WC100',35,4,3);