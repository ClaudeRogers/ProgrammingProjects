var USER_DATA = {
  name: 'Tyler McGinnis',
  img: 'https://avatars0.githubusercontent.com/u/2933430?v=3&s=460',
  username: 'tylermcginnis'
};

function Badge(props) {
  return React.createElement(
    'div',
    null,
    React.createElement('img', { src: props.user.img }),
    React.createElement(
      'h1',
      null,
      'Name: ',
      props.user.name
    ),
    React.createElement(
      'h3',
      null,
      'username: ',
      props.user.username
    )
  );
}

ReactDOM.render(React.createElement(Badge, { user: USER_DATA }), document.getElementById('app'));