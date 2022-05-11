function Make(props) {

    const make = props.makeObj;
    console.log(props.makeObj);
    console.log(make);

    return (
        <option value={make.makeId}>{make.makeName}</option>
    )

}

export default Make;