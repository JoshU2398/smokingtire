import Welcome from "./Welcome";

function Home() {
    return (
        <div className="home-page">
            <div className="container">
                <div id="carCarousel" className="carousel slide carousel-fade" data-bs-ride="carousel">
                    <div className="carousel-inner">
                        <div className="carousel-item active">
                            <img src={process.env.PUBLIC_URL + "corvette.webp"} className="d-block w-100" alt="America's Sports Car" />
                        </div>
                        <div className="carousel-item">
                            <img src={process.env.PUBLIC_URL + "bmwm3.webp"} className="d-block w-100" alt="The Ultimate Driving Machine" />
                        </div>
                        <div className="carousel-item">
                            <img src={process.env.PUBLIC_URL + "toyotasupra.jpg"} className="d-block w-100" alt="The Millenial's Dream Ride" />
                        </div>
                    </div>
                    <button className="carousel-control-prev" type="button" data-bs-target="#carCarousel" data-bs-slide="prev">
                        <span className="carousel-control-prev-icon" aria-hidden="true"></span>
                        <span className="visually-hidden">Previous</span>
                    </button>
                    <button className="carousel-control-next" type="button" data-bs-target="#carCarousel" data-bs-slide="next">
                        <span className="carousel-control-next-icon" aria-hidden="true"></span>
                        <span className="visually-hidden">Next</span>
                    </button>
                </div>
                <Welcome />
            </div>
        </div>
    )
}

export default Home;


/*Use for fetch request: https://vpic.nhtsa.dot.gov/api/vehicles/getallmakes?format=json */


/*Dropdown: 
<select className="form-select" id="base" {...register("base")}>
    <option value="make/model/state"> make/model/state</option>




</select>*/