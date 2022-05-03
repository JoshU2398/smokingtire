import {useState, useEffect} from 'react';


function Listings(){
    const [listings, setListings] = useState([]);

    useEffect(() => {
        fetch("http://localhost")
    })
}