## Transformation du json en sql
### >>>> Tous les détails concernant le projet sont le README_2.md <<<< ####
### SQL pour Les recettes
```js
// jsonText est le fichier json de base
jsonText = jsonText.map(x => {
  	let virgule = /'/gi
  	x.title = x.title.replace(virgule,"''")
  	x.description = x.description.replace(virgule,"''")
  	x.source = x.source.replace(virgule,"''")
    x.source_type = x.source_type.replace(virgule,"''")
    return x;
})
var insert = "INSERT INTO `ApiMascret`.`Recette` (`key`, `title`, `description`, `source`, `source_type`) VALUES (_key,'_title','_description','_src','_SrcType');\n"
var finale = ""
jsonText.forEach(x=>{
  	let key = /_key/gi
    let title = /_title/gi
   	let description = /_description/gi
   	let source = /_src/gi
   	let source_type = /_SrcType/gi
	finale += insert.replace(key,x.key).replace(title,x.title).replace(description,x.description).replace(source,x.source).replace(source_type,x.source_type);
})
console.log(finale)
```
### SQL pour Les ingrédients par recette
```js
// jsonText est le fichier json de base
var finale = "";
var insert = "INSERT INTO `Recette_Ingredient`(`ingrédient`, `recette_needed`) VALUES (_string,_key);\n"
jsonText.forEach(element => {
	var virgule 	= /'/gi
	var ingredient	= /_string/gi
	var key 		= /_key/gi
	element.ingredients.forEach(ingredientString => {
		finale += insert.replace(ingredient,ingredientString.replace(ingredient,"''")).replace(key,element.key)
	})
})
console.log(finale);
```
