import { useEffect, useState } from "react";
import Make from "./Make";
import Model from "./Model";

function Form({childToParent}) {

    const [makes, setMakes] = useState([]);
    const [makeId, setMakeId] = useState(null);

    const [models, setModels] = useState([]);
    const [modelId, setModelId] = useState("Model");

    useEffect(() => {
        fetch("http://localhost:8080/api/makes")
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
    console.log(makes)

    function makeFactory() {
        return makes.map(make => <Make
                key={make.makeId}
                makeObj={make}
            />);
    }

    function findModels() {
        console.log(makeId);

        fetch("http://localhost:8080/api/models/findByMake/" + makeId)
            .then(response => {
                if (response.status === 200) {
                    return response.json();
                } else {
                    alert("Something went wrong while fetching...");
                }
            })
            .then(modelsData => setModels(modelsData))
            .catch(rejection => {
                alert("Failure: " + rejection.status + ": " + rejection.statusText)
            });
    }

    function modelFactory() {
        if (models != null || models != undefined) {
            return models.map(model => <Model
                    key={model.modelId}
                    modelObj={model}
                />);
         }
    }

    return (
        <div className="dropdowns">
            <select className="make-dropdown" id="makes" placeholder="Make"
              onChange={(e) => setMakeId(e.target.value)}>
                {makeFactory()}
            </select>

            {makeId != null || makeId != undefined ? 
                <>
                {findModels()}
                <select className="model-dropdown" id="models" placeholder="Model"
                    onChange={(e) => setModelId(e.target.value)}>
                    {modelFactory()}
                </select>
                </>
            :
                <select value={modelId}></select>
            }
            <button primary onClick={() => childToParent(makeId, modelId)}>Confirm</button>
        </div>
    );
}

export default Form;