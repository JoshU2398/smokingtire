import { useEffect, useState } from "react";
import Make from "./Make";
import Model from "./Model";

function Form(props) {

    const [makes, setMakes] = useState([]);
    const [makeId, setMakeId] = useState(null);

    const [models, setModels] = useState([]);
    const [modelId, setModelId] = useState(null);

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
                alert("Failure: There is an issue with the server. make")
            });
    }, []);

    useEffect(() => {
        if (makeId) {
            findModels();
        }
    }, [makeId])

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
                alert("Failure: There is an issue with the server. model")
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
            <form onSubmit={() => props.childToParent(makeId, modelId)}>
                <select className="make-dropdown" id="makes" defaultValue={{ label: "Select Make", value: null }}
                onClick={(e) => setMakeId(e.target.value)}>
                    {makeFactory()}
                </select>

                {makeId != null || makeId != undefined ? 
                    <>
                    <select className="model-dropdown" id="models"
                    onClick={(e) => setModelId(e.target.value)}>
                        {modelFactory()}
                    </select>
                    </>
                :
                    <select value={modelId}></select>
                }
                <button>Confirm</button>
            </form>
        </div>
    );
}

export default Form;