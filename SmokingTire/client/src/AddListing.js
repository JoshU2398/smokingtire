import { useContext, useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { useForm } from "react-hook-form";
import AuthContext from "./AuthContext";
import Make from "./Make";
import Model from "./Model";

function AddListing() {

    const { register, handleSubmit, formState: { errors } } = useForm();
    const [listing, setListing] = useState({});

    const [price, setPrice] = useState(0);
    const [mileage, setMileage] = useState(0);
    const [description, setDescription] = useState("");
    const [viewCount, setViewCount] = useState(0);
    const [user, setUser] = useContext(AuthContext);
    const [car, setCar] = useState({});
    const [imageUrl, setImageUrl] = useState("");

    const [postDate, setPostDate] = useState("2022-05-12");
    const [isAvailable, setAvailable] = useState(true);
    const [toAdd, setToAdd] = useState(null);

    const nav = useNavigate();
    const jwt = localStorage.getItem("token");

    const [makes, setMakes] = useState([]);
    const [makeId, setMakeId] = useState(null);

    const [models, setModels] = useState([]);
    const [modelId, setModelId] = useState("Model");


    useEffect(
        () => {
            if (jwt) {
                fetch("http://smokingtiresui-env-1.eba-2r42cd2t.us-east-1.elasticbeanstalk.com/api/security/findUser/" + user.user.sub,
                    {
                        headers: {
                            Authorization: "Bearer " + jwt
                        }
                    })
                    .then(response => {
                        if (response.status == 200) {
                            return response.json();
                        } else {
                            console.log(response);
                            alert("retrieving toAdd failed");
                        }
                    })
                    .then(retrievedUser => {
                        console.log(retrievedUser);
                        setToAdd(retrievedUser);
                    })
                    .catch(rejection => {
                        console.log(rejection);
                        alert("Something very bad happened...");
                    });
            } else {
                nav("/login");
            }
        },
        []
    );

    useEffect(() => {
        fetch("http://smokingtiresui-env-1.eba-2r42cd2t.us-east-1.elasticbeanstalk.com/api/makes")
            .then(response => {
                if (response.status === 200) {
                    return response.json();
                } else {
                    alert("Something went wrong while fetching...");
                }
            })
            .then(makesData => setMakes(makesData))
            .catch(rejection => {
                alert("Failure: " + rejection.status + ": " + rejection.statusText)
            });
    }, []);

    useEffect(() => {
        if (makeId) {
            findModels();
        }
    }, [makeId])


    function addPriceHandler(e) {
        setPrice(e.target.value);
    }

    function addDescriptionHandler(e) {
        setDescription(e.target.value);
    }

    function addMileageHandler(e) {
        setMileage(e.target.value);
    }

    function findCar() {
        console.log(modelId);
        fetch("http://smokingtiresui-env-1.eba-2r42cd2t.us-east-1.elasticbeanstalk.com/api/cars/" + modelId)
            .then(response => {
                if (response.status === 200) {
                    return (response.json());
                } else {
                    alert("Something went wrong while fetching the car.");
                }
            })
            .then(carData => {
                console.log(carData);
                setCar(carData);})
            .catch(rejection => {
                alert("Failure to connect with server probably.");
            });
    }

    function findModels() {
        fetch("http://smokingtiresui-env-1.eba-2r42cd2t.us-east-1.elasticbeanstalk.com/api/models/findByMake/" + makeId)
            .then(response => {
                if (response.status === 200) {
                    return response.json();
                } else {
                    alert("Something went wrong while fetching models");
                }
            })
            .then(modelsData => setModels(modelsData))
            .catch(rejection => {
                alert("Failure: " + rejection.status + ": " + rejection.statusText)
            });
    }

    function makeFactory() {
        return makes.map(make => <Make
            key={make.makeId}
            makeObj={make}
        />);
    }

    function modelFactory() {
        if (models != null || models != undefined) {
            return models.map(model => <Model
                key={model.modelId}
                modelObj={model}
            />);
        }
    }

    function uploadImage(e) {
        e.preventDefault();

        let data = new FormData();

        data.append("file", document.getElementById("uploadInput").files[0]);
        setImageUrl(document.getElementById("uploadInput").files[0].name);

        fetch("http://smokingtiresui-env-1.eba-2r42cd2t.us-east-1.elasticbeanstalk.com/api/image/" + modelId, {
            method: "POST",
            body: data
        })
    }

    function onSubmit() {

        findCar();
        console.log(car);
        const updatedUser = { userId: toAdd.userId, username: toAdd.username, password: toAdd.password, roles: toAdd.roles };

        let newListing = {
            price: price,
            mileage: mileage,
            description: description,
            viewCount: viewCount,
            postDate: postDate,
            isAvailable: isAvailable,
            listingUser: updatedUser,
            car: car,
            imageUrl: imageUrl
        };
        console.log(newListing);

        fetch("http://smokingtiresui-env-1.eba-2r42cd2t.us-east-1.elasticbeanstalk.com/api/listings/add", {
            method: "POST",
            headers: {
                Authorization: "Bearer " + jwt,
                "Content-Type": "application/json"
            },
            body: JSON.stringify(newListing)
        })
            .then(response => {
                if (response.status === 400) {
                    console.log(response);
                    alert("Something went wrong");
                    nav("/addListing");
                } if(response.status === 201) {
                    alert("You have successfully created your listing.");
                    nav("/userpage")
                }
            })
            .catch(
                rejection => console.log("failure ", rejection)
            );
    }

    return (
        <div className="row">
            <div className="col" />
            <div className="col-6">
                <div className="semi-opaque form-card">
                    <form onSubmit={handleSubmit(onSubmit)}>
                        <label className="form-label" htmlFor="price">Enter Listing Price:</label><br />
                        <input className="form-control" name="price" placeholder="0" onChange={addPriceHandler} /><br />

                        <label className="form-label" htmlFor="make">Car Make:</label><br />
                        <select className="form-select" id="make" {...register("make")} onClick={(e) => setMakeId(e.target.value)}>
                            {makeFactory()}
                        </select><br /><br />

                        <label className="form-label" htmlFor="make">Car Model:</label><br />
                        <select className="form-select" id="model" {...register("model")} onClick={(e) => setModelId(e.target.value)}>
                            {modelFactory()}
                        </select><br /><br />

                        <label className="form-label" htmlFor="description">Description</label><br />
                        <textarea name="description" placeholder="description" onChange={addDescriptionHandler}></textarea><br /><br />

                        <label className="form-label" htmlFor="mileage">Enter Current Mileage:</label>
                        <input className="form-control" name="mileage" onChange={addMileageHandler} /><br /><br />

                        <input id="uploadInput" type="file" onChange={uploadImage} /><br />

                        <button type="submit">Submit</button>
                    </form>
                </div>
            </div>
            <div className="col" />
        </div>

    )
}

export default AddListing;