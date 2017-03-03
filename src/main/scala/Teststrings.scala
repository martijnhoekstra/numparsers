package parsenums

object Teststrings {

	//bool: true | false case insensitive

	//byte: -128 to (+)127
	//short: -32,768 to (+)32,767
	//int: -2,147,483,648 to (+)2,147,483,647
	//long: -9,223,372,036,854,775,808 to (+)9,223,372,036,854,775,807

	val invalid0 = ""
	val invalid1 = "-"
	val invalid2 = "+"

	val byte1 = "123"
	val byte2 = "-123"
	val byte3 = "+123"
	
	val byte4 = "000123"
	val byte5 = "-000123"
	val byte6 = "+000123"

	val byteoverflow1 = "128"
	val byteoverflow2 = "-129"
	val byteoverflow3 = "+128"

	val ninechars = "123456789"
	val b8 = "-123456789"
	val b9 = "+123456789"

}