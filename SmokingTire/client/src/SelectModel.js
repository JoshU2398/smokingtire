import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

function SelectModel() {

    const [makes, setMakes] = useState(null);
    const [models, setModels] = useState(null);

    const navigate = useNavigate();
    const jwt = localStorage.getItem("token");

    useEffect(() => {

        if(jwt){
            fetch("http://localhost:8080/api/makes", 
            {
                headers: {
                    Authorization: "Bearer " + jwt
                }
            })
            .then(response => {
                if(response.status === 200){
                    return response.json();
                } else {
                    console.log(response);
                    alert("Was not able to find the makes");
                }
            })
            .then(retrievedMakes => {
                console.log(retrievedMakes);
                setMakes(retrievedMakes);
            })
            .catch(rejection => {
                console.log(rejection);
                alert("probably failed to turn on your server.");
            });
        } else {
            navigate("/login")
        }

    }, []);

    



return <p>something</p>
}

export default SelectModel;