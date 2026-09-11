import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import './styles.css';

import api from '../../services/api';

import logo from '../../assets/logo.svg';
import padlock from '../../assets/padlock.png';

export default function Login() {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');

    const navigate = useNavigate();

    async function login(e) {
        e.preventDefault();
        const data = {
            username,
            password
        };

        try {
            const response = await api.post('/auth/signin', data);
            localStorage.setItem('username', username);
            localStorage.setItem('accessToken', response.data.accessToken);

            navigate('/books');
        } catch (err) {
            alert('Login failed, please check your credentials.');
        }
    }

    return (
        <div className="login-container">
            <section className="form">
                <img src={logo} alt="Logo in Login screen" />

                <form onSubmit={login}>
                    <h1>Access your account</h1>
                    <input
                        value={username}
                        placeholder="Username"
                        onChange={(e) => setUsername(e.target.value)}
                    />
                    <input
                        value={password}
                        type="password"
                        placeholder="Password"
                        onChange={(e) => setPassword(e.target.value)}
                    />
                    <button className="button" type="submit">Login</button>
                </form>
            </section>

            <img src={padlock} alt="Padlock in Login screen" />
        </div>
    )
}