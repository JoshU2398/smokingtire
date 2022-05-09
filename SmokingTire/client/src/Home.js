import Welcome from "./Welcome";

function Home() {
    return (
        <>
            <Welcome />
        </>
    )
}

export default Home;


/*Use for fetch request: https://vpic.nhtsa.dot.gov/api/vehicles/getallmakes?format=json */


/*Dropdown: 
<select className="form-select" id="base" {...register("base")}>
    <option value="make/model/state"> make/model/state</option>




</select>*/