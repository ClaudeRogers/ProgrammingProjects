function Avatar(props) {
  //return <img src={props.img} />
  return React.createElement('img', { src: props.img }, null);
}

function Label(props) {
  //return <h1>Name: {props.name}</h1>
  return React.createElement('h1', null, props.name);
}

function ScreenName(props) {
  //return <h3>Username: {props.username}</h3>
  return React.createElement('h3', null, props.username);
}

function Badge(props) {
  // return (
  // <div>
  //   <Avatar img={props.user.img}/>
  //   <Label name={props.user.name}/>
  //   <ScreenName username={props.user.username}/>
  // </div>
  // )

  return React.createElement('div', null, React.createElement(Avatar, { img: props.user.img }), React.createElement(Label, { name: props.user.name }), React.createElement(ScreenName, { username: props.user.username }));
}

ReactDOM.render(
// <Badge user={{
//   name: 'Tyler McGinnis',
//   img: 'https://avatars0.githubusercontent.com/u/2933430?v=3&s=460',
//   username: 'tylermcginnis'
// }} />,
// document.getElementById('app')

React.createElement(Badge, { user: {
    name: 'Tyler McGinnis',
    img: 'https://avatars0.githubusercontent.com/u/2933430?v=3&s=460',
    username: 'tylermcginnis'
  } }), document.getElementById('app'));