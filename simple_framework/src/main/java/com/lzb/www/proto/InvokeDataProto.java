// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: InvokeData.proto

package com.lzb.www.proto;

public final class InvokeDataProto {
  private InvokeDataProto() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  public interface InvokeDataOrBuilder extends
      // @@protoc_insertion_point(interface_extends:com.lzb.www.proto.InvokeData)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>string id = 1;</code>
     * @return The id.
     */
    java.lang.String getId();
    /**
     * <code>string id = 1;</code>
     * @return The bytes for id.
     */
    com.google.protobuf.ByteString
        getIdBytes();

    /**
     * <code>string interfaceName = 2;</code>
     * @return The interfaceName.
     */
    java.lang.String getInterfaceName();
    /**
     * <code>string interfaceName = 2;</code>
     * @return The bytes for interfaceName.
     */
    com.google.protobuf.ByteString
        getInterfaceNameBytes();

    /**
     * <code>string methodName = 3;</code>
     * @return The methodName.
     */
    java.lang.String getMethodName();
    /**
     * <code>string methodName = 3;</code>
     * @return The bytes for methodName.
     */
    com.google.protobuf.ByteString
        getMethodNameBytes();

    /**
     * <code>bytes rpcRequestByteArray = 4;</code>
     * @return The rpcRequestByteArray.
     */
    com.google.protobuf.ByteString getRpcRequestByteArray();
  }
  /**
   * Protobuf type {@code com.lzb.www.proto.InvokeData}
   */
  public static final class InvokeData extends
      com.google.protobuf.GeneratedMessageV3 implements
      // @@protoc_insertion_point(message_implements:com.lzb.www.proto.InvokeData)
      InvokeDataOrBuilder {
  private static final long serialVersionUID = 0L;
    // Use InvokeData.newBuilder() to construct.
    private InvokeData(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }
    private InvokeData() {
      id_ = "";
      interfaceName_ = "";
      methodName_ = "";
      rpcRequestByteArray_ = com.google.protobuf.ByteString.EMPTY;
    }

    @java.lang.Override
    @SuppressWarnings({"unused"})
    protected java.lang.Object newInstance(
        UnusedPrivateParameter unused) {
      return new InvokeData();
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
    getUnknownFields() {
      return this.unknownFields;
    }
    private InvokeData(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      this();
      if (extensionRegistry == null) {
        throw new java.lang.NullPointerException();
      }
      com.google.protobuf.UnknownFieldSet.Builder unknownFields =
          com.google.protobuf.UnknownFieldSet.newBuilder();
      try {
        boolean done = false;
        while (!done) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              done = true;
              break;
            case 10: {
              java.lang.String s = input.readStringRequireUtf8();

              id_ = s;
              break;
            }
            case 18: {
              java.lang.String s = input.readStringRequireUtf8();

              interfaceName_ = s;
              break;
            }
            case 26: {
              java.lang.String s = input.readStringRequireUtf8();

              methodName_ = s;
              break;
            }
            case 34: {

              rpcRequestByteArray_ = input.readBytes();
              break;
            }
            default: {
              if (!parseUnknownField(
                  input, unknownFields, extensionRegistry, tag)) {
                done = true;
              }
              break;
            }
          }
        }
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.setUnfinishedMessage(this);
      } catch (java.io.IOException e) {
        throw new com.google.protobuf.InvalidProtocolBufferException(
            e).setUnfinishedMessage(this);
      } finally {
        this.unknownFields = unknownFields.build();
        makeExtensionsImmutable();
      }
    }
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return com.lzb.www.proto.InvokeDataProto.internal_static_com_lzb_www_proto_InvokeData_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.lzb.www.proto.InvokeDataProto.internal_static_com_lzb_www_proto_InvokeData_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.lzb.www.proto.InvokeDataProto.InvokeData.class, com.lzb.www.proto.InvokeDataProto.InvokeData.Builder.class);
    }

    public static final int ID_FIELD_NUMBER = 1;
    private volatile java.lang.Object id_;
    /**
     * <code>string id = 1;</code>
     * @return The id.
     */
    @java.lang.Override
    public java.lang.String getId() {
      java.lang.Object ref = id_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = 
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        id_ = s;
        return s;
      }
    }
    /**
     * <code>string id = 1;</code>
     * @return The bytes for id.
     */
    @java.lang.Override
    public com.google.protobuf.ByteString
        getIdBytes() {
      java.lang.Object ref = id_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        id_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int INTERFACENAME_FIELD_NUMBER = 2;
    private volatile java.lang.Object interfaceName_;
    /**
     * <code>string interfaceName = 2;</code>
     * @return The interfaceName.
     */
    @java.lang.Override
    public java.lang.String getInterfaceName() {
      java.lang.Object ref = interfaceName_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = 
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        interfaceName_ = s;
        return s;
      }
    }
    /**
     * <code>string interfaceName = 2;</code>
     * @return The bytes for interfaceName.
     */
    @java.lang.Override
    public com.google.protobuf.ByteString
        getInterfaceNameBytes() {
      java.lang.Object ref = interfaceName_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        interfaceName_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int METHODNAME_FIELD_NUMBER = 3;
    private volatile java.lang.Object methodName_;
    /**
     * <code>string methodName = 3;</code>
     * @return The methodName.
     */
    @java.lang.Override
    public java.lang.String getMethodName() {
      java.lang.Object ref = methodName_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = 
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        methodName_ = s;
        return s;
      }
    }
    /**
     * <code>string methodName = 3;</code>
     * @return The bytes for methodName.
     */
    @java.lang.Override
    public com.google.protobuf.ByteString
        getMethodNameBytes() {
      java.lang.Object ref = methodName_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        methodName_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int RPCREQUESTBYTEARRAY_FIELD_NUMBER = 4;
    private com.google.protobuf.ByteString rpcRequestByteArray_;
    /**
     * <code>bytes rpcRequestByteArray = 4;</code>
     * @return The rpcRequestByteArray.
     */
    @java.lang.Override
    public com.google.protobuf.ByteString getRpcRequestByteArray() {
      return rpcRequestByteArray_;
    }

    private byte memoizedIsInitialized = -1;
    @java.lang.Override
    public final boolean isInitialized() {
      byte isInitialized = memoizedIsInitialized;
      if (isInitialized == 1) return true;
      if (isInitialized == 0) return false;

      memoizedIsInitialized = 1;
      return true;
    }

    @java.lang.Override
    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      if (!getIdBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 1, id_);
      }
      if (!getInterfaceNameBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 2, interfaceName_);
      }
      if (!getMethodNameBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 3, methodName_);
      }
      if (!rpcRequestByteArray_.isEmpty()) {
        output.writeBytes(4, rpcRequestByteArray_);
      }
      unknownFields.writeTo(output);
    }

    @java.lang.Override
    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      if (!getIdBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(1, id_);
      }
      if (!getInterfaceNameBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(2, interfaceName_);
      }
      if (!getMethodNameBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(3, methodName_);
      }
      if (!rpcRequestByteArray_.isEmpty()) {
        size += com.google.protobuf.CodedOutputStream
          .computeBytesSize(4, rpcRequestByteArray_);
      }
      size += unknownFields.getSerializedSize();
      memoizedSize = size;
      return size;
    }

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this) {
       return true;
      }
      if (!(obj instanceof com.lzb.www.proto.InvokeDataProto.InvokeData)) {
        return super.equals(obj);
      }
      com.lzb.www.proto.InvokeDataProto.InvokeData other = (com.lzb.www.proto.InvokeDataProto.InvokeData) obj;

      if (!getId()
          .equals(other.getId())) return false;
      if (!getInterfaceName()
          .equals(other.getInterfaceName())) return false;
      if (!getMethodName()
          .equals(other.getMethodName())) return false;
      if (!getRpcRequestByteArray()
          .equals(other.getRpcRequestByteArray())) return false;
      if (!unknownFields.equals(other.unknownFields)) return false;
      return true;
    }

    @java.lang.Override
    public int hashCode() {
      if (memoizedHashCode != 0) {
        return memoizedHashCode;
      }
      int hash = 41;
      hash = (19 * hash) + getDescriptor().hashCode();
      hash = (37 * hash) + ID_FIELD_NUMBER;
      hash = (53 * hash) + getId().hashCode();
      hash = (37 * hash) + INTERFACENAME_FIELD_NUMBER;
      hash = (53 * hash) + getInterfaceName().hashCode();
      hash = (37 * hash) + METHODNAME_FIELD_NUMBER;
      hash = (53 * hash) + getMethodName().hashCode();
      hash = (37 * hash) + RPCREQUESTBYTEARRAY_FIELD_NUMBER;
      hash = (53 * hash) + getRpcRequestByteArray().hashCode();
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static com.lzb.www.proto.InvokeDataProto.InvokeData parseFrom(
        java.nio.ByteBuffer data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.lzb.www.proto.InvokeDataProto.InvokeData parseFrom(
        java.nio.ByteBuffer data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.lzb.www.proto.InvokeDataProto.InvokeData parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.lzb.www.proto.InvokeDataProto.InvokeData parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.lzb.www.proto.InvokeDataProto.InvokeData parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.lzb.www.proto.InvokeDataProto.InvokeData parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.lzb.www.proto.InvokeDataProto.InvokeData parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static com.lzb.www.proto.InvokeDataProto.InvokeData parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }
    public static com.lzb.www.proto.InvokeDataProto.InvokeData parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input);
    }
    public static com.lzb.www.proto.InvokeDataProto.InvokeData parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }
    public static com.lzb.www.proto.InvokeDataProto.InvokeData parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static com.lzb.www.proto.InvokeDataProto.InvokeData parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }

    @java.lang.Override
    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder() {
      return DEFAULT_INSTANCE.toBuilder();
    }
    public static Builder newBuilder(com.lzb.www.proto.InvokeDataProto.InvokeData prototype) {
      return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
    }
    @java.lang.Override
    public Builder toBuilder() {
      return this == DEFAULT_INSTANCE
          ? new Builder() : new Builder().mergeFrom(this);
    }

    @java.lang.Override
    protected Builder newBuilderForType(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      Builder builder = new Builder(parent);
      return builder;
    }
    /**
     * Protobuf type {@code com.lzb.www.proto.InvokeData}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:com.lzb.www.proto.InvokeData)
        com.lzb.www.proto.InvokeDataProto.InvokeDataOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return com.lzb.www.proto.InvokeDataProto.internal_static_com_lzb_www_proto_InvokeData_descriptor;
      }

      @java.lang.Override
      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return com.lzb.www.proto.InvokeDataProto.internal_static_com_lzb_www_proto_InvokeData_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                com.lzb.www.proto.InvokeDataProto.InvokeData.class, com.lzb.www.proto.InvokeDataProto.InvokeData.Builder.class);
      }

      // Construct using com.lzb.www.proto.InvokeDataProto.InvokeData.newBuilder()
      private Builder() {
        maybeForceBuilderInitialization();
      }

      private Builder(
          com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
        super(parent);
        maybeForceBuilderInitialization();
      }
      private void maybeForceBuilderInitialization() {
        if (com.google.protobuf.GeneratedMessageV3
                .alwaysUseFieldBuilders) {
        }
      }
      @java.lang.Override
      public Builder clear() {
        super.clear();
        id_ = "";

        interfaceName_ = "";

        methodName_ = "";

        rpcRequestByteArray_ = com.google.protobuf.ByteString.EMPTY;

        return this;
      }

      @java.lang.Override
      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return com.lzb.www.proto.InvokeDataProto.internal_static_com_lzb_www_proto_InvokeData_descriptor;
      }

      @java.lang.Override
      public com.lzb.www.proto.InvokeDataProto.InvokeData getDefaultInstanceForType() {
        return com.lzb.www.proto.InvokeDataProto.InvokeData.getDefaultInstance();
      }

      @java.lang.Override
      public com.lzb.www.proto.InvokeDataProto.InvokeData build() {
        com.lzb.www.proto.InvokeDataProto.InvokeData result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      @java.lang.Override
      public com.lzb.www.proto.InvokeDataProto.InvokeData buildPartial() {
        com.lzb.www.proto.InvokeDataProto.InvokeData result = new com.lzb.www.proto.InvokeDataProto.InvokeData(this);
        result.id_ = id_;
        result.interfaceName_ = interfaceName_;
        result.methodName_ = methodName_;
        result.rpcRequestByteArray_ = rpcRequestByteArray_;
        onBuilt();
        return result;
      }

      @java.lang.Override
      public Builder clone() {
        return super.clone();
      }
      @java.lang.Override
      public Builder setField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          java.lang.Object value) {
        return super.setField(field, value);
      }
      @java.lang.Override
      public Builder clearField(
          com.google.protobuf.Descriptors.FieldDescriptor field) {
        return super.clearField(field);
      }
      @java.lang.Override
      public Builder clearOneof(
          com.google.protobuf.Descriptors.OneofDescriptor oneof) {
        return super.clearOneof(oneof);
      }
      @java.lang.Override
      public Builder setRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          int index, java.lang.Object value) {
        return super.setRepeatedField(field, index, value);
      }
      @java.lang.Override
      public Builder addRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          java.lang.Object value) {
        return super.addRepeatedField(field, value);
      }
      @java.lang.Override
      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof com.lzb.www.proto.InvokeDataProto.InvokeData) {
          return mergeFrom((com.lzb.www.proto.InvokeDataProto.InvokeData)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(com.lzb.www.proto.InvokeDataProto.InvokeData other) {
        if (other == com.lzb.www.proto.InvokeDataProto.InvokeData.getDefaultInstance()) return this;
        if (!other.getId().isEmpty()) {
          id_ = other.id_;
          onChanged();
        }
        if (!other.getInterfaceName().isEmpty()) {
          interfaceName_ = other.interfaceName_;
          onChanged();
        }
        if (!other.getMethodName().isEmpty()) {
          methodName_ = other.methodName_;
          onChanged();
        }
        if (other.getRpcRequestByteArray() != com.google.protobuf.ByteString.EMPTY) {
          setRpcRequestByteArray(other.getRpcRequestByteArray());
        }
        this.mergeUnknownFields(other.unknownFields);
        onChanged();
        return this;
      }

      @java.lang.Override
      public final boolean isInitialized() {
        return true;
      }

      @java.lang.Override
      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        com.lzb.www.proto.InvokeDataProto.InvokeData parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (com.lzb.www.proto.InvokeDataProto.InvokeData) e.getUnfinishedMessage();
          throw e.unwrapIOException();
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }

      private java.lang.Object id_ = "";
      /**
       * <code>string id = 1;</code>
       * @return The id.
       */
      public java.lang.String getId() {
        java.lang.Object ref = id_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs =
              (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          id_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string id = 1;</code>
       * @return The bytes for id.
       */
      public com.google.protobuf.ByteString
          getIdBytes() {
        java.lang.Object ref = id_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b = 
              com.google.protobuf.ByteString.copyFromUtf8(
                  (java.lang.String) ref);
          id_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string id = 1;</code>
       * @param value The id to set.
       * @return This builder for chaining.
       */
      public Builder setId(
          java.lang.String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  
        id_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string id = 1;</code>
       * @return This builder for chaining.
       */
      public Builder clearId() {
        
        id_ = getDefaultInstance().getId();
        onChanged();
        return this;
      }
      /**
       * <code>string id = 1;</code>
       * @param value The bytes for id to set.
       * @return This builder for chaining.
       */
      public Builder setIdBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
        
        id_ = value;
        onChanged();
        return this;
      }

      private java.lang.Object interfaceName_ = "";
      /**
       * <code>string interfaceName = 2;</code>
       * @return The interfaceName.
       */
      public java.lang.String getInterfaceName() {
        java.lang.Object ref = interfaceName_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs =
              (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          interfaceName_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string interfaceName = 2;</code>
       * @return The bytes for interfaceName.
       */
      public com.google.protobuf.ByteString
          getInterfaceNameBytes() {
        java.lang.Object ref = interfaceName_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b = 
              com.google.protobuf.ByteString.copyFromUtf8(
                  (java.lang.String) ref);
          interfaceName_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string interfaceName = 2;</code>
       * @param value The interfaceName to set.
       * @return This builder for chaining.
       */
      public Builder setInterfaceName(
          java.lang.String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  
        interfaceName_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string interfaceName = 2;</code>
       * @return This builder for chaining.
       */
      public Builder clearInterfaceName() {
        
        interfaceName_ = getDefaultInstance().getInterfaceName();
        onChanged();
        return this;
      }
      /**
       * <code>string interfaceName = 2;</code>
       * @param value The bytes for interfaceName to set.
       * @return This builder for chaining.
       */
      public Builder setInterfaceNameBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
        
        interfaceName_ = value;
        onChanged();
        return this;
      }

      private java.lang.Object methodName_ = "";
      /**
       * <code>string methodName = 3;</code>
       * @return The methodName.
       */
      public java.lang.String getMethodName() {
        java.lang.Object ref = methodName_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs =
              (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          methodName_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string methodName = 3;</code>
       * @return The bytes for methodName.
       */
      public com.google.protobuf.ByteString
          getMethodNameBytes() {
        java.lang.Object ref = methodName_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b = 
              com.google.protobuf.ByteString.copyFromUtf8(
                  (java.lang.String) ref);
          methodName_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string methodName = 3;</code>
       * @param value The methodName to set.
       * @return This builder for chaining.
       */
      public Builder setMethodName(
          java.lang.String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  
        methodName_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string methodName = 3;</code>
       * @return This builder for chaining.
       */
      public Builder clearMethodName() {
        
        methodName_ = getDefaultInstance().getMethodName();
        onChanged();
        return this;
      }
      /**
       * <code>string methodName = 3;</code>
       * @param value The bytes for methodName to set.
       * @return This builder for chaining.
       */
      public Builder setMethodNameBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
        
        methodName_ = value;
        onChanged();
        return this;
      }

      private com.google.protobuf.ByteString rpcRequestByteArray_ = com.google.protobuf.ByteString.EMPTY;
      /**
       * <code>bytes rpcRequestByteArray = 4;</code>
       * @return The rpcRequestByteArray.
       */
      @java.lang.Override
      public com.google.protobuf.ByteString getRpcRequestByteArray() {
        return rpcRequestByteArray_;
      }
      /**
       * <code>bytes rpcRequestByteArray = 4;</code>
       * @param value The rpcRequestByteArray to set.
       * @return This builder for chaining.
       */
      public Builder setRpcRequestByteArray(com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  
        rpcRequestByteArray_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>bytes rpcRequestByteArray = 4;</code>
       * @return This builder for chaining.
       */
      public Builder clearRpcRequestByteArray() {
        
        rpcRequestByteArray_ = getDefaultInstance().getRpcRequestByteArray();
        onChanged();
        return this;
      }
      @java.lang.Override
      public final Builder setUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return super.setUnknownFields(unknownFields);
      }

      @java.lang.Override
      public final Builder mergeUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return super.mergeUnknownFields(unknownFields);
      }


      // @@protoc_insertion_point(builder_scope:com.lzb.www.proto.InvokeData)
    }

    // @@protoc_insertion_point(class_scope:com.lzb.www.proto.InvokeData)
    private static final com.lzb.www.proto.InvokeDataProto.InvokeData DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new com.lzb.www.proto.InvokeDataProto.InvokeData();
    }

    public static com.lzb.www.proto.InvokeDataProto.InvokeData getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<InvokeData>
        PARSER = new com.google.protobuf.AbstractParser<InvokeData>() {
      @java.lang.Override
      public InvokeData parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        return new InvokeData(input, extensionRegistry);
      }
    };

    public static com.google.protobuf.Parser<InvokeData> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<InvokeData> getParserForType() {
      return PARSER;
    }

    @java.lang.Override
    public com.lzb.www.proto.InvokeDataProto.InvokeData getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_com_lzb_www_proto_InvokeData_descriptor;
  private static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_com_lzb_www_proto_InvokeData_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\020InvokeData.proto\022\021com.lzb.www.proto\"`\n" +
      "\nInvokeData\022\n\n\002id\030\001 \001(\t\022\025\n\rinterfaceName" +
      "\030\002 \001(\t\022\022\n\nmethodName\030\003 \001(\t\022\033\n\023rpcRequest" +
      "ByteArray\030\004 \001(\014B$\n\021com.lzb.www.protoB\017In" +
      "vokeDataProtob\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        });
    internal_static_com_lzb_www_proto_InvokeData_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_com_lzb_www_proto_InvokeData_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_com_lzb_www_proto_InvokeData_descriptor,
        new java.lang.String[] { "Id", "InterfaceName", "MethodName", "RpcRequestByteArray", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
