package guru.springframework.bootstrap;

import guru.springframework.domain.*;
import guru.springframework.repositories.CategoryRepository;
import guru.springframework.repositories.RecipeRepository;
import guru.springframework.repositories.UnitOfMeasureRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.StreamSupport;

@Component
public class DataLoader implements CommandLineRunner {

    private final CategoryRepository categoryRepository;
    private final RecipeRepository recipeRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    public DataLoader(CategoryRepository categoryRepository, RecipeRepository recipeRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.categoryRepository = categoryRepository;
        this.recipeRepository = recipeRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @Override
    public void run(String... args) {
        this.loadData();
    }

    private void loadData() {
        Map<String, UnitOfMeasure> uomMap = this.uomMapByDescriptionBuilder();
        UnitOfMeasure uomTeaspoon = uomMap.get("Teaspoon");
        UnitOfMeasure uomTablespoon = uomMap.get("Tablespoon");
        UnitOfMeasure uomCup = uomMap.get("Cup");
        UnitOfMeasure uomPinch = uomMap.get("Pinch");
        UnitOfMeasure uomOunce = uomMap.get("Ounce");
        UnitOfMeasure uomVoid = uomMap.get("");
        UnitOfMeasure uomClove = uomMap.get("Clove");
        UnitOfMeasure uomPint = uomMap.get("Pint");

        Category mexicanCategory = this.categoryFinder("Mexican");

        Recipe perfectGuacamole = this.guacamoleRecipeBuilder(uomMap);
        Recipe tacos = this.spicyGrilledChickenTacosRecipeBuilder(uomMap);
    }

    private Recipe guacamoleRecipeBuilder(Map<String, UnitOfMeasure> uomMap) {
        Recipe perfectGuacamole = new Recipe();
        UnitOfMeasure uomTeaspoon = uomMap.get("Teaspoon");
        UnitOfMeasure uomTablespoon = uomMap.get("Tablespoon");
        UnitOfMeasure uomPinch = uomMap.get("Pinch");
        UnitOfMeasure uomVoid = uomMap.get("");

        perfectGuacamole.setDescription("Perfect guacamole");
        perfectGuacamole.setPrepTime(10);
        perfectGuacamole.setCookTime(0);
        perfectGuacamole.setServings(4);
        perfectGuacamole.setSource("Elise Bauer");
        perfectGuacamole.setUrl("https://www.simplyrecipes.com/recipes/perfect_guacamole/");
        perfectGuacamole.setDirections(
                "1. Cut the avocados in half. Remove the pit. Score the inside of the avocado with a blunt knife and scoop out the flesh with a spoon. Place in a bowl.\n" +
                        "2. Using a fork, roughly mash the avocado. (Don't overdo it! The guacamole should be a little chunky.)" +
                        "3. Sprinkle with salt and lime (or lemon) juice. The acid in the lime juice will provide some balance to the richness of the avocado and will help delay the avocados from turning brown.\n" +
                        "4. Add the chopped onion, cilantro, black pepper, and chilis. Chili peppers vary individually in their spiciness. So, start with a half of one chili pepper and add more to the guacamole to your desired degree of heat.\n" +
                        "5. Garnish with slices of red radish or jicama strips. Serve with your choice of store-bought tortilla chips or make your own homemade tortilla chips."
        );

        Set<Ingredient> ingredients = new HashSet<>();
        ingredients.add(new Ingredient("ripe avocados", BigDecimal.valueOf(2L), uomVoid));
        ingredients.add(new Ingredient("salt", BigDecimal.valueOf(0.25), uomTeaspoon));
        ingredients.add(new Ingredient("fresh lime or lemon juice", BigDecimal.ONE, uomTablespoon));
        ingredients.add(new Ingredient("minced red onion", BigDecimal.valueOf(3), uomTablespoon));
        ingredients.add(new Ingredient("serrano chili, stems and seeds removed, minced", BigDecimal.ONE, uomVoid));
        ingredients.add(new Ingredient("cilantro", BigDecimal.valueOf(2), uomTablespoon));
        ingredients.add(new Ingredient("freshly ground black pepper", BigDecimal.ONE, uomPinch));
        ingredients.add(new Ingredient("ripe tomato, chopped", BigDecimal.valueOf(0.5), uomVoid));
        perfectGuacamole.setIngredients(ingredients);
        perfectGuacamole.setImage(null);
        perfectGuacamole.setDifficulty(Difficulty.EASY);
        Notes guacNotes = new Notes();
        guacNotes.setRecipeNotes("Be careful handling chilis! If using, it's best to wear food-safe gloves. If no gloves are available, wash your hands thoroughly after handling, and do not touch your eyes or the area near your eyes for several hours afterwards.");
        guacNotes.setRecipe(perfectGuacamole);
        perfectGuacamole.setNotes(guacNotes);

        return recipeRepository.save(perfectGuacamole);
    }

    private Recipe spicyGrilledChickenTacosRecipeBuilder(Map<String, UnitOfMeasure> uomMap) {
        Recipe tacos = new Recipe();
        UnitOfMeasure uomTeaspoon = uomMap.get("Teaspoon");
        UnitOfMeasure uomTablespoon = uomMap.get("Tablespoon");
        UnitOfMeasure uomCup = uomMap.get("Cup");
        UnitOfMeasure uomPinch = uomMap.get("Pinch");
        UnitOfMeasure uomOunce = uomMap.get("Ounce");
        UnitOfMeasure uomVoid = uomMap.get("");
        UnitOfMeasure uomClove = uomMap.get("Clove");
        UnitOfMeasure uomPint = uomMap.get("Pint");

        tacos.setDescription("Spicy Grilled Chicken Tacos");
        tacos.setPrepTime(20);
        tacos.setCookTime(15);
        tacos.setServings(4);
        tacos.setSource("Sally Vargas");
        tacos.setUrl("https://www.simplyrecipes.com/recipes/spicy_grilled_chicken_tacos/");
        tacos.setDirections(
                "1. Prepare a gas or charcoal grill for medium-high, direct heat.\n" +
                        "2. Make the marinade and coat the chicken.\n" +
                        "3. Grill the chicken\n" +
                        "4. Warm the tortillas\n" +
                        "5. Assemble the tacos"
        );
        Set<Ingredient> ingredients = new HashSet<>();
        ingredients.add(new Ingredient("Ancho Chili Powder", BigDecimal.valueOf(2), uomTablespoon));
        ingredients.add(new Ingredient("Dried Oregano", BigDecimal.ONE, uomTeaspoon));
        ingredients.add(new Ingredient("Dried Cumin", BigDecimal.ONE, uomTeaspoon));
        ingredients.add(new Ingredient("Sugar", BigDecimal.ONE, uomTeaspoon));
        ingredients.add(new Ingredient("Salt", BigDecimal.valueOf(0.5), uomTeaspoon));
        ingredients.add(new Ingredient("Garlic, Finely Chopped", BigDecimal.ONE, uomClove));
        ingredients.add(new Ingredient("Finely Grated Orange Zest", BigDecimal.ONE, uomTablespoon));
        ingredients.add(new Ingredient("Fresh Squeezed Orange Juice", BigDecimal.valueOf(3), uomTablespoon));
        ingredients.add(new Ingredient("Olive Oil", BigDecimal.valueOf(2), uomTablespoon));
        ingredients.add(new Ingredient("Skinless, Boneless Chicken Thighs", BigDecimal.valueOf(6), uomVoid));
        ingredients.add(new Ingredient("Small Corn Tortillas", BigDecimal.valueOf(8), uomVoid));
        ingredients.add(new Ingredient("Packed Baby Arugula", BigDecimal.valueOf(3), uomCup));
        ingredients.add(new Ingredient("medium ripe avocados, sliced", BigDecimal.valueOf(2), uomVoid));
        ingredients.add(new Ingredient("radishes, thinly sliced", BigDecimal.valueOf(4), uomVoid));
        ingredients.add(new Ingredient("cherry tomatoes, halved", BigDecimal.valueOf(0.5), uomPint));
        ingredients.add(new Ingredient("red onion, thinly sliced", BigDecimal.valueOf(0.25), uomVoid));
        ingredients.add(new Ingredient("Roughly chopped cilantro", BigDecimal.ONE, uomVoid));
        ingredients.add(new Ingredient("sour cream", BigDecimal.valueOf(0.5), uomCup));
        ingredients.add(new Ingredient("milk", BigDecimal.valueOf(0.25), uomCup));
        ingredients.add(new Ingredient("lime, cut into wedges", BigDecimal.ONE, uomVoid));
        tacos.setIngredients(ingredients);
        tacos.setImage(null);
        tacos.setDifficulty(Difficulty.KIND_OF_HARD);
        Notes tacoNotes = new Notes();
        tacoNotes.setRecipeNotes("Be careful handling chilis! If using, it's best to wear food-safe gloves. If no gloves are available, wash your hands thoroughly after handling, and do not touch your eyes or the area near your eyes for several hours afterwards.");
        tacoNotes.setRecipe(tacos);
        tacos.setNotes(tacoNotes);

        return recipeRepository.save(tacos);
    }

    private Map<String, UnitOfMeasure> uomMapByDescriptionBuilder() {
        HashMap<String, UnitOfMeasure> uomMap = new HashMap<>();
        StreamSupport.stream(unitOfMeasureRepository.findAll().spliterator(), false).forEach(uom -> uomMap.put(uom.getDescription(), uom));
        return uomMap;
    }

    private Category categoryFinder(String description) {
        return this.categoryRepository.findByDescription(description).get();
    }

//    private final Map<String, String> uomPlurals = new HashMap<>();
//    {
//        // customized plurals
//        uomPlurals.put("", "");
//        uomPlurals.put("Pinch", "Pinches");
//        uomPlurals.put("Ounce", "Ounces");
//    }

//    private String pluralizeUom(String singularUomDescription) {
//        if (!uomPlurals.containsKey(singularUomDescription)) {
//            uomPlurals.put(singularUomDescription, singularUomDescription + 's');
//        }
//        return uomPlurals.get(singularUomDescription);
//    }
}
