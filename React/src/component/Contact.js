import React from 'react';
import { Container } from 'react-bootstrap';
import styled from 'styled-components';
import { FaPhone, FaMapMarkerAlt, FaClock } from 'react-icons/fa';

const PageContainer = styled(Container)`
  padding: 20px;
  min-height: 100vh;
  background-image: url('https://img.freepik.com/free-photo/audio-headset-used-by-call-center-agents-help-clients-telecommunication-with-technology-empty-customer-service-workstation-with-headphones-computers-modern-gadgets_482257-40834.jpg?w=900&t=st=1691207165~exp=1691207765~hmac=30887e21e642139fd4eddfbc945b7d22e31d99d34a45538ea45cba1887a1cf65');
  background-size: cover;
  background-position: center;
  display: flex;
  justify-content: center;
  align-items: center;
`;

const ContactInfoContainer = styled.div`
  background-color: rgba(255, 255, 255, 0.7); /* Adjust alpha (opacity) value */
  border-radius: 10px;
  padding: 20px;
  box-shadow: 0px 4px 6px rgba(0, 0, 0, 0.1);
  text-align: center;
`;

const ContactTitle = styled.h2`
  font-size: 28px;
  margin-bottom: 20px;
  color: #333;
`;

const ContactDetail = styled.p`
  font-size: 18px;
  margin: 10px 0;
  color: #555;
  display: flex;
  align-items: center;
`;

const Icon = styled.span`
  font-size: 24px;
  margin-right: 10px;
  color: #007bff;
`;

function Contact() {
  return (
    <PageContainer fluid>
      <ContactInfoContainer>
        <ContactTitle>Contact Us</ContactTitle>
        <ContactDetail>
          <Icon><FaPhone /></Icon>
          Landline: + 01282-272-535
        </ContactDetail>
        <ContactDetail>
          <Icon><FaPhone /></Icon>
          Phone: +91 82955 188 35
        </ContactDetail>
        <ContactDetail>
          <Icon><FaMapMarkerAlt /></Icon>
          Address: 123  Rangoli Main Street, Jaipur, India
        </ContactDetail>
        <ContactDetail>
          <Icon><FaClock /></Icon>
          Working Hours: Monday - Friday, 9:00 AM - 5:00 PM
        </ContactDetail>
      </ContactInfoContainer>
    </PageContainer>
  );
}

export default Contact;
