function Avatar(props) {
  return React.createElement('img', { src: props.img });
}

function Label(props) {
  return React.createElement(
    'h1',
    null,
    'Name: ',
    props.name
  );
}

function ScreenName(props) {
  return React.createElement(
    'h3',
    null,
    'Username: ',
    props.username
  );
}

function Badge(props) {
  return React.createElement(
    'div',
    null,
    React.createElement(Avatar, { img: props.user.img }),
    React.createElement(Label, { name: props.user.name }),
    React.createElement(ScreenName, { username: props.user.username })
  );
}

ReactDOM.render(React.createElement(Badge, { user: {
    name: 'Tyler McGinnis',
    img: 'https://avatars0.githubusercontent.com/u/2933430?v=3&s=460',
    username: 'tylermcginnis'
  } }), document.getElementById('app'));