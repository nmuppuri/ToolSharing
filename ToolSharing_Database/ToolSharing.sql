/*	*****TEAM MEMBERS*****

Narendra Kumar Muppuri
Narender Reddy Gayam
Narasimha Reddy Patel
Arun Kumbapillil Ravi
Sai Ram Pramodhini Reddy Aleti
Usha Lakhani


*/


DROP SCHEMA IF EXISTS ToolSharing ;
CREATE SCHEMA IF NOT EXISTS ToolSharing DEFAULT CHARACTER SET UTF8 ;
USE ToolSharing ;

DROP TABLE IF EXISTS person;
CREATE TABLE IF NOT EXISTS person (
    id				INT PRIMARY KEY NOT NULL,
    passwd			VARCHAR(50) 	NULL,
    first_name		VARCHAR(50) 	NOT NULL,
    last_name		VARCHAR(50) 	NOT NULL,
    email			VARCHAR(50) 	NOT NULL,
    phone			BIGINT,
    address			VARCHAR(255),
    admin_access	VARCHAR(10)
);


DROP TABLE IF EXISTS student;
CREATE TABLE IF NOT EXISTS student (
    student_id INT PRIMARY KEY NOT NULL,
    FOREIGN KEY (student_id)
        REFERENCES person (id)
);


DROP TABLE IF EXISTS admins;
CREATE TABLE IF NOT EXISTS admins (
    admin_id INT PRIMARY KEY NOT NULL,
    FOREIGN KEY (admin_id)
        REFERENCES person (id)
);


DROP TABLE IF EXISTS student_registration;
CREATE TABLE IF NOT EXISTS student_registration (
    student_id INT NOT NULL,
    delete_request INT NOT NULL,
    decision VARCHAR(50) NOT NULL,
    decision_date DATE,
    PRIMARY KEY (student_id, delete_request),
    FOREIGN KEY (student_id)
        REFERENCES person (id)
);



DROP TABLE IF EXISTS tools;
CREATE TABLE IF NOT EXISTS tools (
    tool_id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    tool_name VARCHAR(50) NOT NULL,
    tool_desc VARCHAR(1000),
    tool_img VARCHAR(500)
);

ALTER TABLE tools AUTO_INCREMENT = 100;


DROP TABLE IF EXISTS student_tools;
CREATE TABLE IF NOT EXISTS student_tools (
	posted_student_id INT NOT NULL,
    posted_tool_id INT NOT NULL,
    availability INT,
    rating FLOAT,
    PRIMARY KEY (posted_student_id, posted_tool_id),
    FOREIGN KEY (posted_student_id)
        REFERENCES student (student_id),
    FOREIGN KEY (posted_tool_id)
        REFERENCES tools (tool_id)
);


DROP TABLE IF EXISTS favorite_tools;
CREATE TABLE IF NOT EXISTS favorite_tools (	
	posted_student_id INT NOT NULL,
    posted_tool_id INT NOT NULL,
    logged_student_id INT NOT NULL,
    favorite INT,
    PRIMARY KEY (posted_student_id, posted_tool_id, logged_student_id),
    FOREIGN KEY (posted_student_id, posted_tool_id)
        REFERENCES student_tools (posted_student_id, posted_tool_id),
    FOREIGN KEY (logged_student_id)
        REFERENCES student (student_id)
);
    



DROP TABLE IF EXISTS order_details;
CREATE TABLE IF NOT EXISTS order_details (
	order_id INT NOT NULL AUTO_INCREMENT,
    posted_tool_id INT NOT NULL,
	posted_student_id INT NOT NULL,
    borrowed_student_id INT NOT NULL,
    from_date DATE NOT NULL,
    to_date DATE NOT NULL,
    returned INT,
    penalty FLOAT,
    PRIMARY KEY (order_id),
    FOREIGN KEY (posted_student_id, posted_tool_id)
        REFERENCES student_tools (posted_student_id, posted_tool_id),
    FOREIGN KEY (borrowed_student_id)
        REFERENCES student (student_id)
);

ALTER TABLE order_details AUTO_INCREMENT = 1000;




DROP TABLE IF EXISTS tools_comment;
CREATE TABLE IF NOT EXISTS tools_comment (
	order_id INT NOT NULL,
    rating FLOAT,
    comments VARCHAR(500),
    PRIMARY KEY (order_id),
    FOREIGN KEY (order_id)
        REFERENCES order_details (order_id)
);



DROP TABLE IF EXISTS message;
CREATE TABLE IF NOT EXISTS message (
    message_id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    message VARCHAR(1000) NOT NULL,
    sent_date TIMESTAMP(6) NOT NULL,
    from_student_id INT NOT NULL,
    FOREIGN KEY (from_student_id)
        REFERENCES student (student_id)
);

ALTER TABLE message AUTO_INCREMENT = 100000;


DROP TABLE IF EXISTS message_recepient;
CREATE TABLE IF NOT EXISTS message_recepient (
    message_id INT NOT NULL,
    to_student_id INT NOT NULL,
    PRIMARY KEY (message_id , to_student_id),
    FOREIGN KEY (to_student_id)
        REFERENCES student (student_id),
    FOREIGN KEY (message_id)
        REFERENCES message (message_id)
);

/*      INSERT STATEMENTS FOR ADMINS ACCESS        */

INSERT INTO toolsharing.person (id, passwd, first_name, last_name, email, phone, address, admin_access) VALUES (17900, 'qwerty', 'Pargol', 'P', 'pargol@cegepgim.ca', NULL, NULL, 'Y'); 

INSERT INTO toolsharing.admins (admin_id) VALUES (17900);

INSERT INTO toolsharing.person (id, passwd, first_name, last_name, email, phone, address, admin_access) VALUES (17901, 'qwerty', 'Reza', 'Madabadi', 'rmadabadi@cegepgim.ca', NULL, NULL, 'Y'); 

INSERT INTO toolsharing.admins (admin_id) VALUES (17901);

INSERT INTO toolsharing.person (id, passwd, first_name, last_name, email, phone, address, admin_access) VALUES (17902, 'qwerty', 'Sakkaravarthi', 'Ramanathan', 'sramanathan@cegepgim.ca', NULL, NULL, 'Y'); 

INSERT INTO toolsharing.admins (admin_id) VALUES (17902);

INSERT INTO toolsharing.person (id, passwd, first_name, last_name, email, phone, address, admin_access) VALUES (17903, 'qwerty', 'Silviya', 'Paskaleva', 'spaskaleva@cegepgim.ca', NULL, NULL, 'Y');

INSERT INTO toolsharing.admins (admin_id) VALUES (17903);

INSERT INTO toolsharing.person (id, passwd, first_name, last_name, email, phone, address, admin_access) VALUES (1794346, 'qwerty', 'GP', 'Gregoire', 'gpgregoire@cegepgim.ca', NULL, NULL, 'Y');

INSERT INTO toolsharing.admins (admin_id) VALUES (1794346);


/*      INSERT STATEMENTS FOR ADD TOOLS LIST        */

INSERT INTO tools(tool_name, tool_desc, tool_img) VALUES ('Circular Saw','In woodworking the term circular saw is most commonly used to refer to a hand-held, electric circular saw designed for cutting wood, but may be used for cutting other materials with different blades. Circular saws can be either left or right-handed, depending on the side of the blade where the motor sits.','https://images.homedepot-static.com/productImages/46755567-66c4-45ec-9e28-cb8d0045dc67/svn/skilsaw-circular-saws-spt77wm-22-64_1000.jpg');

INSERT INTO tools(tool_name, tool_desc, tool_img) VALUES ('Reciprocating Saws','A reciprocating saw is a popular tool used by many window fitters, construction workers and emergency rescue services. Variants and accessories are available for specialized uses, such as clamps and long blades for cutting large pipe. Blades are available for a variety of materials and uses.','https://images.homedepot-static.com/productImages/e3fada2d-8c8a-41d5-a5f7-8466f6c1b1ed/svn/ryobi-reciprocating-saws-p517-64_1000.jpg');

INSERT INTO tools(tool_name, tool_desc, tool_img) VALUES ('Jig saws','The hallmark of a jigsaw is cutting circles, scrolls and other shapes but it can also be used to cut along a straight line. Use a saw guide or rip fence to help keep steady along straight cuts. A framing square can be used as a guide for shorter straight cuts. This is particularly useful when making a bevel cut.','https://images.homedepot-static.com/productImages/91fe8f1a-327a-4cbf-965c-1d5f124f2446/svn/ryobi-jigsaws-p524-64_400_compressed.jpg');

INSERT INTO tools(tool_name, tool_desc, tool_img) VALUES ('Miter Saws','A miter saw is a specialized tool that lets you make cuts at a variety of angles. The saw has a blade mounted on a swing arm that pivots left or right to produce angled cuts. You can use a miter saw to quickly make cuts for crown moulding, picture frames, door frames, window casings and more.','https://contentgrid.homedepot-static.com/hdus/en_US/DTCCOMNEW/fetch/FetchRules/PLP_Banner_PartialGroup/milwaukee-miter-saws-plp.jpg');

INSERT INTO tools(tool_name, tool_desc, tool_img) VALUES ('Table Saws','A table saw is a woodworking tool, consisting of a circular saw blade, mounted on an arbor, that is driven by an electric motor. The blade protrudes through the top of a table, which provides support for the material, usually wood, being cut.','https://images.homedepot-static.com/productImages/d011379b-2593-4d0e-a111-4789a4156c2e/svn/ridgid-portable-table-saws-r4518-64_400_compressed.jpg');

INSERT INTO tools(tool_name, tool_desc, tool_img) VALUES ('Band Saws','A band saw can be used to cut curves, even in thick lumber, such as in creating cabriole legs, to rip lumber and to crosscut short pieces. The most common use for the band saw, however, is in cutting irregular shapes. The second most common use is in resawing or ripping lumber into thinner slabs.','https://images.homedepot-static.com/productImages/a87f7623-1bd1-4cdd-8bcf-deb3087a2a6c/svn/ryobi-stationary-band-saws-bs904g-64_400_compressed.jpg');

INSERT INTO tools(tool_name, tool_desc, tool_img) VALUES ('Belt Sanders','A belt sander or strip sander is a sander used in shaping and finishing wood and other materials. It consists of an electric motor that turns a pair of drums on which a continuous loop of sandpaper is mounted.','https://images.homedepot-static.com/productImages/54dc661c-cc85-4152-a74f-0dcb9353ed4b/svn/ridgid-belt-sanders-r27401-64_400_compressed.jpg');

INSERT INTO tools(tool_name, tool_desc, tool_img) VALUES ('Disc & Orbital Sanders','Orbital sander: A hand-held sander that vibrates in small circles, or "orbits." The sanding disk spinning while moving simultaneously in small ellipses causes the orbital action that it is known for. Mostly used for fine sanding or where little material needs to be removed.','https://images.homedepot-static.com/productImages/8a73ed11-474c-472e-b978-3c11feaad7dc/svn/ryobi-disc-orbital-sanders-p411-64_400_compressed.jpg');

INSERT INTO tools(tool_name, tool_desc, tool_img) VALUES ('Sheet Sanders','Sheet sanders are built to work with partial sheets of standard sandpaper. ... The sheets are cut into halves or quarters and are then securely clamped onto the flat base of the sander.','https://images.homedepot-static.com/productImages/fa8b2846-134c-4940-8fa3-641a8a616cb0/svn/porter-cable-drywall-sanders-7800-64_400_compressed.jpg');

INSERT INTO tools(tool_name, tool_desc, tool_img) VALUES ('Spindle Sanders','The spindle sander is a tool created and designed to help you achieve a smooth, flat finish to wood pieces, mostly those that are detailed or have curved edges. Gone are the days of rubbing away at your handmade banister or chair with a piece of worn sandpaper.','https://images.homedepot-static.com/productImages/025d4f4d-b5fc-4949-828e-213a46002e2e/svn/wen-spindle-sanders-6510t-64_400_compressed.jpg');

INSERT INTO tools(tool_name, tool_desc, tool_img) VALUES ('Sanding Discs','The Disk Sander is used to smooth materials such as woods and plastics. It is also used to remove small amounts of waste material. It is a dangerous machine if safety is ignored. ... This is very important as it will extract dust particles produced when the machine is in use','https://images.homedepot-static.com/productImages/26d862e3-e92d-4a64-96f4-b584d0b6b473/svn/diablo-power-sander-accessories-dnd050400h10i-64_400_compressed.jpg');

INSERT INTO tools(tool_name, tool_desc, tool_img) VALUES ('Fixed Base Routers','Fixed base routers begin with the depth already set. Consequently, the bit is already lowered beneath the base of the router when you turn it on. Fixed router bits tend to be flat on the bottom, instead of pointed.','https://images.homedepot-static.com/productImages/8b8f700c-a539-450d-9848-dd5cd077f9f0/svn/ridgid-corded-routers-r24011-64_400_compressed.jpg');

INSERT INTO tools(tool_name, tool_desc, tool_img) VALUES ('Plunge Routers','A plunge base router is designed so that you can preset the cut depth and then lower (“plunge”) the bit into the cut with the router`s base flat on the surface of the material','https://images.homedepot-static.com/productImages/2fd40086-3db3-4cd9-8b65-05d9262276ea/svn/makita-corded-routers-rp2301fc-64_400_compressed.jpg');

INSERT INTO tools(tool_name, tool_desc, tool_img) VALUES ('Router Bits','Straight router bits are among the most common frequently used of all bits. Straight bits used to make cuts straight down into a material to form a groove or dado or to hollow out an area for a mortise or inlay. Straight bits come in a variety of cut diameters, most commonly in the range from 3/16" to 1-1/2"','https://images.homedepot-static.com/productImages/6bf68a53-642b-4b10-ac68-bd97c913a42c/svn/professional-woodworker-straight-spiral-router-bits-7744-64_400_compressed.jpg');

INSERT INTO tools(tool_name, tool_desc, tool_img) VALUES ('Workbench Accessories','There are some most common accessories like bench vise, bench dog, workmate, workbench mat, pliers, wrench rail or organizer, pegboard, safety or warning tape and so on.','https://images.homedepot-static.com/productImages/fd2c48d5-3d6e-4b7d-923c-fbf828353e88/svn/delta-workbench-accessories-17-985-64_400_compressed.jpg');

INSERT INTO tools(tool_name, tool_desc, tool_img) VALUES ('Workbench Accessories','There are some most common accessories like bench vise, bench dog, workmate, workbench mat, pliers, wrench rail or organizer, pegboard, safety or warning tape and so on.','https://images.homedepot-static.com/productImages/136d1ac3-d3f9-4de4-9937-c87c2ae1ee35/svn/delta-workbench-accessories-17-005-64_400_compressed.jpg');

INSERT INTO tools(tool_name, tool_desc, tool_img) VALUES ('Workbench Accessories','There are some most common accessories like bench vise, bench dog, workmate, workbench mat, pliers, wrench rail or organizer, pegboard, safety or warning tape and so on.','https://images.homedepot-static.com/productImages/3c2b62ef-a1ff-4a2a-8411-16886cb1bbf5/svn/milwaukee-workbench-accessories-48-08-0260-64_400_compressed.jpg');

INSERT INTO tools(tool_name, tool_desc, tool_img) VALUES ('Workbench Accessories','There are some most common accessories like bench vise, bench dog, workmate, workbench mat, pliers, wrench rail or organizer, pegboard, safety or warning tape and so on.','https://images.homedepot-static.com/productImages/0c4fb043-c4af-4534-bf41-e7df7150effa/svn/jet-workbench-accessories-708320-64_400_compressed.jpg');

INSERT INTO tools(tool_name, tool_desc, tool_img) VALUES ('Workbench Accessories','There are some most common accessories like bench vise, bench dog, workmate, workbench mat, pliers, wrench rail or organizer, pegboard, safety or warning tape and so on.','https://images.homedepot-static.com/productImages/3a61f0cd-b8fb-4343-83ed-d5c905f7b991/svn/nova-workbench-accessories-5015-64_400_compressed.jpg');

INSERT INTO tools(tool_name, tool_desc, tool_img) VALUES ('Workbench Accessories','There are some most common accessories like bench vise, bench dog, workmate, workbench mat, pliers, wrench rail or organizer, pegboard, safety or warning tape and so on.','https://images.homedepot-static.com/productImages/407c472c-14f0-44b9-a844-74a2c83daa05/svn/jet-workbench-accessories-708326-64_400_compressed.jpg');

INSERT INTO tools(tool_name, tool_desc, tool_img) VALUES ('Planer Blades','A planer blade is a woodworking tool used to create a seamless planed surfaces on the wood. A plane uses muscle power to ensure that the cutting blade works as desired and as such, there is a need to understand how to sharpen the blade. ... The material that you intend to remove while sharpening the blade','https://images.homedepot-static.com/productImages/1bfed47a-e748-4bc5-9af2-4ec8972c7dfa/svn/makita-planer-blades-d-46230-64_400_compressed.jpg');

INSERT INTO tools(tool_name, tool_desc, tool_img) VALUES ('Rabbeting Router Bits','A rabbeting router bit helps you make them all, and cuts rascally rabbets on curved edges, such as an arch-topped door—something not possible with a tablesaw.','https://images.homedepot-static.com/productImages/3cd8a866-466b-4ed0-8e36-43d7ef12eac1/svn/ryobi-router-bit-sets-a25r151-64_400_compressed.jpg');

INSERT INTO tools(tool_name, tool_desc, tool_img) VALUES ('Router Bushings','Guide bushings are simple devices that when used properly, extend the capabilities of your router. The bushing fits into the hole in the routers baseplate and is designed to follow a template, which guides the router bit, transferring the templates shape to another piece of wood.','https://images.homedepot-static.com/productImages/96961c8b-15a0-436d-b123-260c75ecbaa0/svn/bosch-router-parts-ra1181-64_400_compressed.jpg');

INSERT INTO tools(tool_name, tool_desc, tool_img) VALUES ('Router Bushings','Guide bushings are simple devices that when used properly, extend the capabilities of your router. The bushing fits into the hole in the routers baseplate and is designed to follow a template, which guides the router bit, transferring the templates shape to another piece of wood.','https://images.homedepot-static.com/productImages/95e870df-75e6-4e04-96bd-2b24f97b5b9d/svn/ryobi-router-parts-a25rt03-64_400_compressed.jpg');

INSERT INTO tools(tool_name, tool_desc, tool_img) VALUES ('Router Bushings','Guide bushings are simple devices that when used properly, extend the capabilities of your router. The bushing fits into the hole in the routers baseplate and is designed to follow a template, which guides the router bit, transferring the templates shape to another piece of wood.','https://images.homedepot-static.com/productImages/b9fa39ad-27c4-4e78-99f8-458ea601c8c6/svn/bosch-router-parts-ra1171-64_400_compressed.jpg');

INSERT INTO tools(tool_name, tool_desc, tool_img) VALUES ('Router Bushings','Guide bushings are simple devices that when used properly, extend the capabilities of your router. The bushing fits into the hole in the routers baseplate and is designed to follow a template, which guides the router bit, transferring the templates shape to another piece of wood.','https://images.homedepot-static.com/productImages/d259cd1d-c804-4183-a5db-5c9c4d4bc803/svn/milescraft-router-parts-1219-64_400_compressed.jpg');

INSERT INTO tools(tool_name, tool_desc, tool_img) VALUES ('Router Bushings','Guide bushings are simple devices that when used properly, extend the capabilities of your router. The bushing fits into the hole in the routers baseplate and is designed to follow a template, which guides the router bit, transferring the templates shape to another piece of wood.','https://images.homedepot-static.com/productImages/c7f8c6c3-9782-4b0e-9a28-d3ee072931b5/svn/porter-cable-router-parts-6931-64_400_compressed.jpg');

INSERT INTO tools(tool_name, tool_desc, tool_img) VALUES ('Router Bushings','Guide bushings are simple devices that when used properly, extend the capabilities of your router. The bushing fits into the hole in the routers baseplate and is designed to follow a template, which guides the router bit, transferring the templates shape to another piece of wood.','https://images.homedepot-static.com/productImages/f3ad23dd-22d4-4f1d-967a-636fe396f191/svn/porter-cable-router-guides-59370-64_400_compressed.jpg');

INSERT INTO tools(tool_name, tool_desc, tool_img) VALUES ('Tool Stands','Tool stands can be of use and value to craftsmen of all abilities. These stands are vital to help keep power tools within easy reach and on a stable work surface, allowing you to run your workspace safely and efficiently.','https://contentgrid.homedepot-static.com/hdus/en_US/DTCCOMNEW/fetch/Category_Pages/Tools_and_Hardware/Power_Tools/Woodworking_Tools/woodworking-2.jpg');

INSERT INTO tools(tool_name, tool_desc, tool_img) VALUES ('Drill Presses','Drilling machines, or drill presses, are primarily used to drill or enlarge a cylindrical hole in a workpiece or part. The chief operation performed on the drill press is drilling, but other possible operations include: reaming, countersinking, counterboring, and tapping.','https://contentgrid.homedepot-static.com/hdus/en_US/DTCCOMNEW/fetch/Category_Pages/Tools_and_Hardware/Power_Tools/Woodworking_Tools/drill-presses-12g.jpg');

INSERT INTO tools(tool_name, tool_desc, tool_img) VALUES ('Nail Guns','A nail gun, nailgun or nailer is a type of tool used to drive nails into wood or some other kind of material. It is usually driven by compressed air (pneumatic), electromagnetism, highly flammable gases such as butane or propane, or, for powder-actuated tools, a small explosive charge.','https://contentgrid.homedepot-static.com/hdus/en_US/DTCCOMNEW/fetch/Category_Pages/Tools_and_Hardware/Power_Tools/Woodworking_Tools/nail-guns-12g.jpg');

INSERT INTO tools(tool_name, tool_desc, tool_img) VALUES ('Rotary Tools','Rotary tool is handheld power tool with rotary tip. Sanding drums can be attached for sanding and carving bevels. Set the rotary tool`s speed at medium when using a sanding drum. Buffing/polishing bits are attached to a rotary tool to add shine to metal surfaces.','https://contentgrid.homedepot-static.com/hdus/en_US/DTCCOMNEW/fetch/Category_Pages/Tools_and_Hardware/Power_Tools/Woodworking_Tools/rotary-tools-12g.jpg');

INSERT INTO tools(tool_name, tool_desc, tool_img) VALUES ('Drills','A drill is a tool primarily used for making round holes or driving fasteners. It is fitted with a bit, either a drill or driver, depending on application, secured by a chuck. Some powered drills also include a hammer function.','https://contentgrid.homedepot-static.com/hdus/en_US/DTCCOMNEW/fetch/Category_Pages/Tools_and_Hardware/Power_Tools/Woodworking_Tools/drills-12g.jpg');

INSERT INTO tools(tool_name, tool_desc, tool_img) VALUES ('Fastening Tools','Fastener tools are often the first to be selected for the handyman`s toolbox. They are simply tools that help you apply fasteners, such as nails, bolts, and adhesives. Fastener tools include hammers, screwdrivers, pliers, and clamps.','https://contentgrid.homedepot-static.com/hdus/en_US/DTCCOMNEW/fetch/Category_Pages/Tools_and_Hardware/Power_Tools/Woodworking_Tools/fastening-tool-12g.jpg');

INSERT INTO tools(tool_name, tool_desc, tool_img) VALUES ('Woodworking Jigs','Woodworking jigs ensure that cuts are straight, holes are plumb and parts are square—among many other things. And jigs are worth the time it takes to make them because you`ll use them over and over again for years.','https://images.homedepot-static.com/productImages/8b32e4f9-78fe-40b5-9c4b-5d8134d268ef/svn/porter-cable-jigs-4216-64_400_compressed.jpg');

INSERT INTO tools(tool_name, tool_desc, tool_img) VALUES ('Chisels, Files & Punches','Punches and chisels have been in use for centuries. ... way to re-sharpen the chisel is to re-file the cutting edge using a hand file, not a grinder.','https://images.homedepot-static.com/productImages/cd56819b-b53b-4099-ba8c-a70b94de4f47/svn/milwaukee-punches-nail-setter-sets-49-16-2694-64_400_compressed.jpg');

INSERT INTO tools(tool_name, tool_desc, tool_img) VALUES ('Measuring Tools','A measuring tool is anything used to take a count of quantitative data, such as weight, length, time or temperature. Rulers and scales are two common types of measuring tools. Measuring tools can be very precise, but low quality ones can lead to faulty measurements.','https://images.homedepot-static.com/productImages/201fcb78-2ca0-4dc1-9d09-765b792f55fc/svn/laser-distance-measurer-dwht43239gc-64_400_compressed.jpg');

INSERT INTO tools(tool_name, tool_desc, tool_img) VALUES ('Hammers','A hammer is a handheld tool used to strike another object. It consists of a handle to which is attached a heavy head, usually made of metal, with one or more striking surfaces. There are dozens of different types of hammers. The most common is a claw hammer, which is used to drive and pull nails.','https://images.homedepot-static.com/productImages/541ad867-3003-4450-bb63-3492c8df6141/svn/husky-claw-hammers-n-g16c20s-hn-64_400_compressed.jpg');

INSERT INTO tools(tool_name, tool_desc, tool_img) VALUES ('Dust Collectors','Dust collectors may be of single unit construction, or a collection of devices used to separate particulate matter from the process air. They are often used as an air pollution control device to maintain or improve air quality. Mist collectors remove particulate matter in the form of fine liquid droplets from the air.','https://images.homedepot-static.com/productImages/16f0d723-92da-4d2e-a851-9583ec8933ad/svn/wen-dust-collectors-air-filtration-3401-64_400_compressed.jpg');

INSERT INTO tools(tool_name, tool_desc, tool_img) VALUES ('Air Filtration','A particulate air filter is a device composed of fibrous or porous materials which removes solid particulates such as dust, pollen, mold, and bacteria from the air.','https://images.homedepot-static.com/productImages/66a21c1c-6157-4f1c-be66-9fd0fd24c9f9/svn/powermatic-dust-collectors-air-filtration-1791331-64_400_compressed.jpg');

INSERT INTO tools(tool_name, tool_desc, tool_img) VALUES ('Wet & Dry Vacuums','A wet and dry vacuum cleaner is similar to a regular vacuum but with the ability to clean both dry and liquid messes regardless of whether it`s inside or outside the home. Instead of a vacuum bag, it usually has a two bucket system that separates the solids from the liquids.','https://images.homedepot-static.com/productImages/fbc8279b-1dc0-4366-94ad-1da02b89c2bc/svn/reds-pinks-milwaukee-wet-dry-vacuums-0885-20-48-11-1850-64_400_compressed.jpg');

INSERT INTO tools(tool_name, tool_desc, tool_img) VALUES ('Planers','Simply put, a wood planer is a woodworking tool, which can be used for producing boards of even thickness that also happen to be totally flat on either side.','https://contentgrid.homedepot-static.com/hdus/en_US/DTCCOMNEW/fetch/Category_Pages/Tools_and_Hardware/Power_Tools/Woodworking_Tools/planers-12g.jpg');

INSERT INTO tools(tool_name, tool_desc, tool_img) VALUES ('Lathes','A lathe is a machine tool that rotates a workpiece about an axis of rotation to perform various operations such as cutting, sanding, knurling, drilling, deformation, facing, and turning, with tools that are applied to the workpiece to create an object with symmetry about that axis.','https://contentgrid.homedepot-static.com/hdus/en_US/DTCCOMNEW/fetch/Category_Pages/Tools_and_Hardware/Power_Tools/Woodworking_Tools/lathes-12g.jpg');

INSERT INTO tools(tool_name, tool_desc, tool_img) VALUES ('Oscillating Tools','An oscillating tool works with a side-to-side movement. The oscillation is very slight (about 3 degrees) and very fast (about 20,000 strokes per minute), so it feels more like vibration. An oscillating saw blade is shown; the oscillating tool also works with scrapers and sanding pads.','https://contentgrid.homedepot-static.com/hdus/en_US/DTCCOMNEW/fetch/Category_Pages/Tools_and_Hardware/Power_Tools/Woodworking_Tools/oscillating-tools-12g.jpg');
