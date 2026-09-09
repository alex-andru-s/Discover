package planet;


public  enum PlanetContentCategory {
	
	TOOL_OBJECT_PROCESSED(
	        "category.tool_object_processed",
	        "question.category.object"),

	PROCESSED_BIOLOGICAL(
	        "category.processed_biological",
	        "question.category.biological"),

    ANIMAL_ARCHITECTURE(
	        "category.animal_architecture",
	        "question.category.animal_architeture"),

    ARTIFICIAL_ENERGY_SYS(
	        "category.artificial_energy_sys",
	        "question.category.energy_system"),

    COMPLEX_INFRASTRUCTURE(
	        "category.complex_infrastructure",
	        "question.category.infrastructure"),

	ELEMENTS_MINERALS(
	        "category.elements_minerals",
	        "question.category.mineral"),

	GEOLOGICAL_FORMATIONS(
	        "category.geological_formations",
	        "question.category.geological_formation"),

	STATES_WATER(
	        "category.states_water",
	        "question.category.water_state"),

   PLANT_KINGDOM(
	        "category.plant_kingdom",
	        "question.category.plant"),

   ANIMAL_KINGDOM(
	        "category.animal_kingdom",
	        "question.category.animal"),

   ENERGY_PHENOMENA(
	        "category.energy_phenomena",
	        "question.category.energy_phenomenon");

   private final String description;
   private final String questionCategory;

PlanetContentCategory(String description,
	                  String questionCategory) {

  this.description = description;
  this.questionCategory = questionCategory;
}

public String getDescription() {
	 return description;
}

public String getQuestionCategory() {
	 return questionCategory;
}

}