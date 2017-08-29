function DateTimeParseTest(A){TestCase.call(this,A)
}DateTimeParseTest.inherits(TestCase);
var DateTimeConstants_en={ERAS:["BC","AD"],ERANAMES:["Before Christ","Anno Domini"],NARROWMONTHS:["J","F","M","A","M","J","J","A","S","O","N","D"],MONTHS:["January","February","March","April","May","June","July","August","September","October","November","December"],SHORTMONTHS:["Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"],WEEKDAYS:["Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"],SHORTWEEKDAYS:["Sun","Mon","Tue","Wed","Thu","Fri","Sat"],NARROWWEEKDAYS:["S","M","T","W","T","F","S"],SHORTQUARTERS:["Q1","Q2","Q3","Q4"],QUARTERS:["1st quarter","2nd quarter","3rd quarter","4th quarter"],AMPMS:["AM","PM"],DATEFORMATS:["EEEE, MMMM d, yyyy","MMMM d, yyyy","MMM d, yyyy","M/d/yy"],TIMEFORMATS:["h:mm:ss a v","h:mm:ss a z","h:mm:ss a","h:mm a"],ZONESTRINGS:null};
DateTimeConstants_en.STANDALONENARROWMONTHS=DateTimeConstants_en.NARROWMONTHS;
DateTimeConstants_en.STANDALONEMONTHS=DateTimeConstants_en.MONTHS;
DateTimeConstants_en.STANDALONESHORTMONTHS=DateTimeConstants_en.SHORTMONTHS;
DateTimeConstants_en.STANDALONEWEEKDAYS=DateTimeConstants_en.WEEKDAYS;
DateTimeConstants_en.STANDALONESHORTWEEKDAYS=DateTimeConstants_en.SHORTWEEKDAYS;
DateTimeConstants_en.STANDALONENARROWWEEKDAYS=DateTimeConstants_en.NARROWWEEKDAYS;
DateTimeParseTest.prototype.setUp=function(){gadgets.i18n.dtParser_=new gadgets.i18n.DateTimeParse(DateTimeConstants_en)
};
DateTimeParseTest.prototype.testNegativeYear=function(){var A=new Date();
this.assertTrue(gadgets.i18n.parseDateTime("MM/dd, yyyy","11/22, 1999",0,A)>0);
this.assertEquals(1999,A.getFullYear());
this.assertEquals(11-1,A.getMonth());
this.assertEquals(22,A.getDate());
this.assertTrue(gadgets.i18n.parseDateTime("MM/dd, yyyy","11/22, -1999",0,A)>0);
this.assertEquals(-1999,A.getFullYear());
this.assertEquals(11-1,A.getMonth());
this.assertEquals(22,A.getDate())
};
DateTimeParseTest.prototype.testEra=function(){var A=new Date();
this.assertTrue(gadgets.i18n.parseDateTime("MM/dd, yyyyG","11/22, 1999BC",0,A)>0);
this.assertEquals(-1998,A.getFullYear());
this.assertEquals(11-1,A.getMonth());
this.assertEquals(22,A.getDate());
this.assertTrue(gadgets.i18n.parseDateTime("MM/dd, yyyyG","11/22, 1BC",0,A)>0);
this.assertEquals(0,A.getFullYear());
this.assertEquals(11-1,A.getMonth());
this.assertEquals(22,A.getDate());
this.assertTrue(gadgets.i18n.parseDateTime("MM/dd, yyyyG","11/22, 1999AD",0,A)>0);
this.assertEquals(1999,A.getFullYear());
this.assertEquals(11-1,A.getMonth());
this.assertEquals(22,A.getDate())
};
DateTimeParseTest.prototype.testFractionalSeconds=function(){var A=new Date();
this.assertTrue(gadgets.i18n.parseDateTime("hh:mm:ss.SSS","11:12:13.956",0,A)>0);
this.assertEquals(11,A.getHours());
this.assertEquals(12,A.getMinutes());
this.assertEquals(13,A.getSeconds());
this.assertEquals(956,A.getTime()%1000);
this.assertTrue(gadgets.i18n.parseDateTime("hh:mm:ss.SSS","11:12:13.95",0,A)>0);
this.assertEquals(11,A.getHours());
this.assertEquals(12,A.getMinutes());
this.assertEquals(13,A.getSeconds());
this.assertEquals(950,A.getTime()%1000);
this.assertTrue(gadgets.i18n.parseDateTime("hh:mm:ss.SSS","11:12:13.9",0,A)>0);
this.assertEquals(11,A.getHours());
this.assertEquals(12,A.getMinutes());
this.assertEquals(13,A.getSeconds());
this.assertEquals(900,A.getTime()%1000)
};
DateTimeParseTest.prototype.testAmbiguousYear=function(){var A=new Date();
var B=new Date();
B.setFullYear(B.getFullYear()+20);
B.setMonth(0);
B.setDate(1);
B.setHours(0);
B.setMinutes(0);
B.setSeconds(0);
B.setMilliseconds(1);
var C="01/01/"+(B.getFullYear()%100);
this.assertTrue(gadgets.i18n.parseDateTime("MM/dd/yy",C,0,A)>0);
this.assertEquals(B.getFullYear(),A.getFullYear());
B.setMonth(11);
B.setDate(31);
B.setHours(23);
B.setMinutes(59);
B.setSeconds(59);
B.setMilliseconds(999);
C="12/31/"+(B.getFullYear()%100);
this.assertTrue(gadgets.i18n.parseDateTime("MM/dd/yy",C,0,A)>0);
this.assertEquals(B.getFullYear(),A.getFullYear()+100);
this.assertTrue(gadgets.i18n.parseDateTime("yy,MM,dd","2097,07,21",0,A)>0);
this.assertEquals(2097,A.getFullYear());
gadgets.i18n.DateTimeParse.ambiguousYearCenturyStart=60;
B.setMonth(0);
B.setDate(1);
B.setHours(0);
B.setMinutes(0);
B.setSeconds(0);
B.setMilliseconds(1);
C="01/01/"+(B.getFullYear()%100);
this.assertTrue(gadgets.i18n.parseDateTime("MM/dd/yy",C,0,A)>0);
this.assertEquals(B.getFullYear(),A.getFullYear());
B.setFullYear(A.getFullYear()+20);
C="01/01/"+(B.getFullYear()%100);
this.assertTrue(gadgets.i18n.parseDateTime("MM/dd/yy",C,0,A)>0);
this.assertEquals(B.getFullYear(),A.getFullYear());
B.setFullYear(A.getFullYear()+21);
C="01/01/"+(B.getFullYear()%100);
this.assertTrue(gadgets.i18n.parseDateTime("MM/dd/yy",C,0,A)>0);
this.assertEquals(B.getFullYear(),A.getFullYear()+100);
gadgets.i18n.DateTimeParse.ambiguousYearCenturyStart=80
};
DateTimeParseTest.prototype.testLeapYear=function(){var A=new Date();
this.assertTrue(gadgets.i18n.parseDateTime("MMdd, yyyy","0229, 2001",0,A)>0);
this.assertEquals(3-1,A.getMonth());
this.assertEquals(1,A.getDate());
this.assertTrue(gadgets.i18n.parseDateTime("MMdd, yyyy","0229, 2000",0,A)>0);
this.assertEquals(2-1,A.getMonth());
this.assertEquals(29,A.getDate())
};
DateTimeParseTest.prototype.testAbutField=function(){var A=new Date();
this.assertTrue(gadgets.i18n.parseDateTime("hhmm","1122",0,A)>0);
this.assertEquals(11,A.getHours());
this.assertEquals(22,A.getMinutes());
this.assertTrue(gadgets.i18n.parseDateTime("hhmm","122",0,A)>0);
this.assertEquals(1,A.getHours());
this.assertEquals(22,A.getMinutes());
this.assertTrue(gadgets.i18n.parseDateTime("hhmmss","112233",0,A)>0);
this.assertEquals(11,A.getHours());
this.assertEquals(22,A.getMinutes());
this.assertEquals(33,A.getSeconds());
this.assertTrue(gadgets.i18n.parseDateTime("hhmmss","12233",0,A)>0);
this.assertEquals(1,A.getHours());
this.assertEquals(22,A.getMinutes());
this.assertEquals(33,A.getSeconds());
this.assertTrue(gadgets.i18n.parseDateTime("yyyyMMdd","19991202",0,A)>0);
this.assertEquals(1999,A.getFullYear());
this.assertEquals(12-1,A.getMonth());
this.assertEquals(2,A.getDate());
this.assertTrue(gadgets.i18n.parseDateTime("yyyyMMdd","9991202",0,A)>0);
this.assertTrue(A.getFullYear()==999);
this.assertEquals(12-1,A.getMonth());
this.assertEquals(2,A.getDate());
this.assertTrue(gadgets.i18n.parseDateTime("yyyyMMdd","991202",0,A)>0);
this.assertEquals(99,A.getFullYear());
this.assertEquals(12-1,A.getMonth());
this.assertEquals(2,A.getDate());
this.assertTrue(gadgets.i18n.parseDateTime("yyyyMMdd","91202",0,A)>0);
this.assertEquals(9,A.getFullYear());
this.assertEquals(12-1,A.getMonth());
this.assertEquals(2,A.getDate())
};
DateTimeParseTest.prototype.testYearParsing=function(){var A=new Date();
this.assertTrue(gadgets.i18n.parseDateTime("yyMMdd","991202",0,A)>0);
this.assertEquals(1999,A.getFullYear());
this.assertEquals(12-1,A.getMonth());
this.assertEquals(2,A.getDate());
this.assertTrue(gadgets.i18n.parseDateTime("yyyyMMdd","20051202",0,A)>0);
this.assertEquals(2005,A.getFullYear());
this.assertEquals(12-1,A.getMonth());
this.assertEquals(2,A.getDate())
};
DateTimeParseTest.prototype.testHourParsing_hh=function(){var A=new Date();
this.assertTrue(gadgets.i18n.parseDateTime("hhmm","0022",0,A)>0);
this.assertEquals(0,A.getHours());
this.assertEquals(22,A.getMinutes());
this.assertTrue(gadgets.i18n.parseDateTime("hhmm","1122",0,A)>0);
this.assertEquals(11,A.getHours());
this.assertEquals(22,A.getMinutes());
this.assertTrue(gadgets.i18n.parseDateTime("hhmm","1222",0,A)>0);
this.assertEquals(0,A.getHours());
this.assertEquals(22,A.getMinutes());
this.assertTrue(gadgets.i18n.parseDateTime("hhmm","2322",0,A)>0);
this.assertEquals(23,A.getHours());
this.assertEquals(22,A.getMinutes());
this.assertTrue(gadgets.i18n.parseDateTime("hhmm","2422",0,A)>0);
this.assertEquals(0,A.getHours());
this.assertEquals(22,A.getMinutes());
this.assertTrue(gadgets.i18n.parseDateTime("hhmma","0022am",0,A)>0);
this.assertEquals(0,A.getHours());
this.assertEquals(22,A.getMinutes());
this.assertTrue(gadgets.i18n.parseDateTime("hhmma","1122am",0,A)>0);
this.assertEquals(11,A.getHours());
this.assertEquals(22,A.getMinutes());
this.assertTrue(gadgets.i18n.parseDateTime("hhmma","1222am",0,A)>0);
this.assertEquals(0,A.getHours());
this.assertEquals(22,A.getMinutes());
this.assertTrue(gadgets.i18n.parseDateTime("hhmma","2322am",0,A)>0);
this.assertEquals(23,A.getHours());
this.assertEquals(22,A.getMinutes());
this.assertTrue(gadgets.i18n.parseDateTime("hhmma","2422am",0,A)>0);
this.assertEquals(0,A.getHours());
this.assertEquals(22,A.getMinutes());
this.assertTrue(gadgets.i18n.parseDateTime("hhmma","0022pm",0,A)>0);
this.assertEquals(12,A.getHours());
this.assertEquals(22,A.getMinutes());
this.assertTrue(gadgets.i18n.parseDateTime("hhmma","1122pm",0,A)>0);
this.assertEquals(23,A.getHours());
this.assertEquals(22,A.getMinutes());
this.assertTrue(gadgets.i18n.parseDateTime("hhmma","1222pm",0,A)>0);
this.assertEquals(12,A.getHours());
this.assertEquals(22,A.getMinutes());
this.assertTrue(gadgets.i18n.parseDateTime("hhmma","2322pm",0,A)>0);
this.assertEquals(23,A.getHours());
this.assertEquals(22,A.getMinutes());
this.assertTrue(gadgets.i18n.parseDateTime("hhmma","2422pm",0,A)>0);
this.assertEquals(0,A.getHours());
this.assertEquals(22,A.getMinutes())
};
DateTimeParseTest.prototype.testHourParsing_KK=function(){var A=new Date();
this.assertTrue(gadgets.i18n.parseDateTime("KKmm","0022",0,A)>0);
this.assertEquals(0,A.getHours());
this.assertEquals(22,A.getMinutes());
this.assertTrue(gadgets.i18n.parseDateTime("KKmm","1122",0,A)>0);
this.assertEquals(11,A.getHours());
this.assertEquals(22,A.getMinutes());
this.assertTrue(gadgets.i18n.parseDateTime("KKmm","1222",0,A)>0);
this.assertEquals(12,A.getHours());
this.assertEquals(22,A.getMinutes());
this.assertTrue(gadgets.i18n.parseDateTime("KKmm","2322",0,A)>0);
this.assertEquals(23,A.getHours());
this.assertEquals(22,A.getMinutes());
this.assertTrue(gadgets.i18n.parseDateTime("KKmm","2422",0,A)>0);
this.assertEquals(0,A.getHours());
this.assertEquals(22,A.getMinutes());
this.assertTrue(gadgets.i18n.parseDateTime("KKmma","0022am",0,A)>0);
this.assertEquals(0,A.getHours());
this.assertEquals(22,A.getMinutes());
this.assertTrue(gadgets.i18n.parseDateTime("KKmma","1122am",0,A)>0);
this.assertEquals(11,A.getHours());
this.assertEquals(22,A.getMinutes());
this.assertTrue(gadgets.i18n.parseDateTime("KKmma","1222am",0,A)>0);
this.assertEquals(12,A.getHours());
this.assertEquals(22,A.getMinutes());
this.assertTrue(gadgets.i18n.parseDateTime("KKmma","2322am",0,A)>0);
this.assertEquals(23,A.getHours());
this.assertEquals(22,A.getMinutes());
this.assertTrue(gadgets.i18n.parseDateTime("KKmma","2422am",0,A)>0);
this.assertEquals(0,A.getHours());
this.assertEquals(22,A.getMinutes());
this.assertTrue(gadgets.i18n.parseDateTime("KKmma","0022pm",0,A)>0);
this.assertEquals(12,A.getHours());
this.assertEquals(22,A.getMinutes());
this.assertTrue(gadgets.i18n.parseDateTime("KKmma","1122pm",0,A)>0);
this.assertEquals(23,A.getHours());
this.assertEquals(22,A.getMinutes());
this.assertTrue(gadgets.i18n.parseDateTime("KKmma","1222pm",0,A)>0);
this.assertEquals(12,A.getHours());
this.assertEquals(22,A.getMinutes());
this.assertTrue(gadgets.i18n.parseDateTime("KKmma","2322pm",0,A)>0);
this.assertEquals(23,A.getHours());
this.assertEquals(22,A.getMinutes());
this.assertTrue(gadgets.i18n.parseDateTime("KKmma","2422pm",0,A)>0);
this.assertEquals(0,A.getHours());
this.assertEquals(22,A.getMinutes())
};
DateTimeParseTest.prototype.testHourParsing_kk=function(){var A=new Date();
this.assertTrue(gadgets.i18n.parseDateTime("kkmm","0022",0,A)>0);
this.assertEquals(0,A.getHours());
this.assertEquals(22,A.getMinutes());
this.assertTrue(gadgets.i18n.parseDateTime("kkmm","1122",0,A)>0);
this.assertEquals(11,A.getHours());
this.assertEquals(22,A.getMinutes());
this.assertTrue(gadgets.i18n.parseDateTime("kkmm","1222",0,A)>0);
this.assertEquals(12,A.getHours());
this.assertEquals(22,A.getMinutes());
this.assertTrue(gadgets.i18n.parseDateTime("kkmm","2322",0,A)>0);
this.assertEquals(23,A.getHours());
this.assertEquals(22,A.getMinutes());
this.assertTrue(gadgets.i18n.parseDateTime("kkmm","2422",0,A)>0);
this.assertEquals(0,A.getHours());
this.assertEquals(22,A.getMinutes());
this.assertTrue(gadgets.i18n.parseDateTime("kkmma","0022am",0,A)>0);
this.assertEquals(0,A.getHours());
this.assertEquals(22,A.getMinutes());
this.assertTrue(gadgets.i18n.parseDateTime("kkmma","1122am",0,A)>0);
this.assertEquals(11,A.getHours());
this.assertEquals(22,A.getMinutes());
this.assertTrue(gadgets.i18n.parseDateTime("kkmma","1222am",0,A)>0);
this.assertEquals(12,A.getHours());
this.assertEquals(22,A.getMinutes());
this.assertTrue(gadgets.i18n.parseDateTime("kkmma","2322am",0,A)>0);
this.assertEquals(23,A.getHours());
this.assertEquals(22,A.getMinutes());
this.assertTrue(gadgets.i18n.parseDateTime("kkmma","2422am",0,A)>0);
this.assertEquals(0,A.getHours());
this.assertEquals(22,A.getMinutes());
this.assertTrue(gadgets.i18n.parseDateTime("kkmma","0022pm",0,A)>0);
this.assertEquals(12,A.getHours());
this.assertEquals(22,A.getMinutes());
this.assertTrue(gadgets.i18n.parseDateTime("kkmma","1122pm",0,A)>0);
this.assertEquals(23,A.getHours());
this.assertEquals(22,A.getMinutes());
this.assertTrue(gadgets.i18n.parseDateTime("kkmma","1222pm",0,A)>0);
this.assertEquals(12,A.getHours());
this.assertEquals(22,A.getMinutes());
this.assertTrue(gadgets.i18n.parseDateTime("kkmma","2322pm",0,A)>0);
this.assertEquals(23,A.getHours());
this.assertEquals(22,A.getMinutes());
this.assertTrue(gadgets.i18n.parseDateTime("kkmma","2422pm",0,A)>0);
this.assertEquals(0,A.getHours());
this.assertEquals(22,A.getMinutes())
};
DateTimeParseTest.prototype.testHourParsing_HH=function(){var A=new Date();
this.assertTrue(gadgets.i18n.parseDateTime("HHmm","0022",0,A)>0);
this.assertEquals(0,A.getHours());
this.assertEquals(22,A.getMinutes());
this.assertTrue(gadgets.i18n.parseDateTime("HHmm","1122",0,A)>0);
this.assertEquals(11,A.getHours());
this.assertEquals(22,A.getMinutes());
this.assertTrue(gadgets.i18n.parseDateTime("HHmm","1222",0,A)>0);
this.assertEquals(12,A.getHours());
this.assertEquals(22,A.getMinutes());
this.assertTrue(gadgets.i18n.parseDateTime("HHmm","2322",0,A)>0);
this.assertEquals(23,A.getHours());
this.assertEquals(22,A.getMinutes());
this.assertTrue(gadgets.i18n.parseDateTime("HHmm","2422",0,A)>0);
this.assertEquals(0,A.getHours());
this.assertEquals(22,A.getMinutes());
this.assertTrue(gadgets.i18n.parseDateTime("HHmma","0022am",0,A)>0);
this.assertEquals(0,A.getHours());
this.assertEquals(22,A.getMinutes());
this.assertTrue(gadgets.i18n.parseDateTime("HHmma","1122am",0,A)>0);
this.assertEquals(11,A.getHours());
this.assertEquals(22,A.getMinutes());
this.assertTrue(gadgets.i18n.parseDateTime("HHmma","1222am",0,A)>0);
this.assertEquals(12,A.getHours());
this.assertEquals(22,A.getMinutes());
this.assertTrue(gadgets.i18n.parseDateTime("HHmma","2322am",0,A)>0);
this.assertEquals(23,A.getHours());
this.assertEquals(22,A.getMinutes());
this.assertTrue(gadgets.i18n.parseDateTime("HHmma","2422am",0,A)>0);
this.assertEquals(0,A.getHours());
this.assertEquals(22,A.getMinutes());
this.assertTrue(gadgets.i18n.parseDateTime("HHmma","0022pm",0,A)>0);
this.assertEquals(12,A.getHours());
this.assertEquals(22,A.getMinutes());
this.assertTrue(gadgets.i18n.parseDateTime("HHmma","1122pm",0,A)>0);
this.assertEquals(23,A.getHours());
this.assertEquals(22,A.getMinutes());
this.assertTrue(gadgets.i18n.parseDateTime("HHmma","1222pm",0,A)>0);
this.assertEquals(12,A.getHours());
this.assertEquals(22,A.getMinutes());
this.assertTrue(gadgets.i18n.parseDateTime("HHmma","2322pm",0,A)>0);
this.assertEquals(23,A.getHours());
this.assertEquals(22,A.getMinutes());
this.assertTrue(gadgets.i18n.parseDateTime("HHmma","2422pm",0,A)>0);
this.assertEquals(0,A.getHours());
this.assertEquals(22,A.getMinutes())
};
DateTimeParseTest.prototype.testEnglishDate=function(){var A=new Date();
this.assertTrue(gadgets.i18n.parseDateTime("yyyy MMM dd hh:mm","2006 Jul 10 15:44",0,A)>0);
this.assertEquals(2006,A.getFullYear());
this.assertEquals(7-1,A.getMonth());
this.assertEquals(10,A.getDate());
this.assertEquals(15,A.getHours());
this.assertEquals(44,A.getMinutes())
};
DateTimeParseTest.prototype.testTimeZone=function(){var E=new Date();
this.assertTrue(gadgets.i18n.parseDateTime("MM/dd/yyyy, hh:mm:ss zzz","07/21/2003, 11:22:33 GMT-0700",0,E)>0);
var C=E.getHours();
this.assertTrue(gadgets.i18n.parseDateTime("MM/dd/yyyy, hh:mm:ss zzz","07/21/2003, 11:22:33 GMT-0600",0,E)>0);
var D=E.getHours();
this.assertEquals(1,(C+24-D)%24);
this.assertTrue(gadgets.i18n.parseDateTime("MM/dd/yyyy, hh:mm:ss zzz","07/21/2003, 11:22:33 GMT-0800",0,E)>0);
var B=E.getHours();
this.assertEquals(1,(B+24-C)%24);
this.assertTrue(gadgets.i18n.parseDateTime("MM/dd/yyyy, HH:mm:ss zzz","07/21/2003, 23:22:33 GMT-0800",0,E)>0);
this.assertEquals((E.getHours()+24-C)%24,13);
this.assertTrue(gadgets.i18n.parseDateTime("MM/dd/yyyy, HH:mm:ss zzz","07/21/2003, 11:22:33 GMT+0800",0,E)>0);
var A=E.getHours();
this.assertEquals(16,(B+24-A)%24);
this.assertTrue(gadgets.i18n.parseDateTime("MM/dd/yyyy, HH:mm:ss zzz","07/21/2003, 11:22:33 GMT0800",0,E)>0);
this.assertEquals(A,E.getHours())
};
DateTimeParseTest.prototype.testWeekDay=function(){var A=new Date();
this.assertTrue(gadgets.i18n.parseDateTime("EEEE, MM/dd/yyyy","Wednesday, 08/16/2006",0,A)>0);
this.assertEquals(2006,A.getFullYear());
this.assertEquals(8-1,A.getMonth());
this.assertEquals(16,A.getDate());
this.assertTrue(gadgets.i18n.parseDateTime("EEEE, MM/dd/yyyy","Tuesday, 08/16/2006",0,A)==0);
this.assertTrue(gadgets.i18n.parseDateTime("EEEE, MM/dd/yyyy","Thursday, 08/16/2006",0,A)==0);
this.assertTrue(gadgets.i18n.parseDateTime("EEEE, MM/dd/yyyy","Wed, 08/16/2006",0,A)>0);
this.assertTrue(gadgets.i18n.parseDateTime("EEEE, MM/dd/yyyy","Wasdfed, 08/16/2006",0,A)==0);
A.setDate(25);
this.assertTrue(gadgets.i18n.parseDateTime("EEEE, MM/yyyy","Wed, 09/2006",0,A)>0);
this.assertEquals(27,A.getDate());
A.setDate(30);
this.assertTrue(gadgets.i18n.parseDateTime("EEEE, MM/yyyy","Wed, 09/2006",0,A)>0);
this.assertEquals(27,A.getDate());
A.setDate(30);
this.assertTrue(gadgets.i18n.parseDateTime("EEEE, MM/yyyy","Mon, 09/2006",0,A)>0);
this.assertEquals(25,A.getDate())
};